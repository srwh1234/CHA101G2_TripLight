package com.tw.ai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tw.ai.config.AppConfig;
import com.tw.ai.dto.AiFormDataDto;
import com.tw.ai.dto.AiLocationsDto;
import com.tw.ai.util.GetLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChatGPTService {

    private final Map<String, String> destinationInput;
    public Map<String,ArrayList<String>> locations;
    private final Map<String, String> output;
    private final GetLocation getLocation;
    private final String API_KEY;
    private static final String MODEL = "gpt-3.5-turbo";
    private static final String URL = "https://api.openai.com/v1/chat/completions";

    private final Logger logger
            = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ChatGPTService(AppConfig appConfig,GetLocation getLocation) {
        this.API_KEY = appConfig.getApiKey();
        this.destinationInput = new ConcurrentHashMap<>();
        this.output = new ConcurrentHashMap<>();
        this.locations = new ConcurrentHashMap<>();
        this.getLocation = getLocation;
    }

    // 這個方法會傳回一個 InputStream，該 InputStream 會連線到 ChatGPT API 並傳送 message
    private BufferedReader chatGPT(String message) throws IOException {
        // 使用 Jackson 的 ObjectMapper 和 ObjectNode 類創建一個 JSON 對象
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();

        // 並且設定一些需要的屬性，如模型名稱（model）、消息數組（messages）
        rootNode.put("model", MODEL);
        ArrayNode messagesNode = rootNode.putArray("messages");
        ObjectNode messageNode = messagesNode.addObject();
        messageNode.put("role", "user");
        messageNode.put("content", message);

        // 設定最大 token 數量（max_tokens）以及是否進行流傳輸（stream）
        rootNode.put("max_tokens", 2500);
        rootNode.put("stream", true);

        // 轉換 JSON 對象為字串：使用 ObjectMapper 的 writeValueAsString 方法將上面創建的 JSON 對象轉換成一個 JSON 字串。
        String data = objectMapper.writeValueAsString(rootNode);

        // 連線到 ChatGPT API
        java.net.URL url = new URL(URL);
        // 使用 java.net.URL 和 HttpURLConnection 類創建一個 HTTP 連接到 GPT-3 API 的 URL。
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 設定請求方法為 POST，並且添加兩個請求頭部
        connection.setRequestMethod("POST");

        // 內容類型（Content-Type），表示我們將要發送的數據是 JSON 格式的
        connection.setRequestProperty("Content-Type", "application/json");

        // 添加另一個授權（Authorization），表示我們有權訪問這個 API
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setDoOutput(true);

        // 發送請求：打開連接的輸出流，將 JSON 字串寫入到輸出流，然後刷新輸出流以確保數據被實際發送出去。
        OutputStream out = connection.getOutputStream();
        out.write(data.getBytes());
        out.flush();

        // 開連接的輸入流，並且將它包裝成一個 BufferedReader 物件。最後，返回這個 BufferedReader 物件，讓我們可以在後續的程式中讀取和處理從 API 返回的數據。
        return new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }

    public void start(String sessionID, AiFormDataDto formData) {
        locations.clear();
        destinationInput.put(sessionID,formData.getDestination());  // 拿取資料
        String message1 = """
你今後的對話中，請幫我規畫行程，請用繁體中文回覆，
花費的部分幫我用當地貨幣計價
格式如下
[天數]:
上午：[行程][花費]
中午：[行程][花費]
下午：[行程][花費]
晚上：[行程][花費]
住宿：[飯店名稱][花費]
所有地點名稱：[地點名稱]
        
備註：[備註]
需考慮每個地點的路程距離，地點名稱須加上地名
        """ + formData.toMessage();

        String tempinput = "";   // 每次呼叫，都會清空內容

        try (BufferedReader chatReader = chatGPT(message1)) {

            // 處理回傳的資料
            while (true) {
                // 讀取 BufferedReader，一行一行地讀取，直到讀到 EOF
                String line = chatReader.readLine();
                if (line == null) {
                    break;
                }
                // 不讀取assistant
                if (line.contains("\"role\":\"assistant\"")) {
                    continue;
                }

                // 檢查是否包含 "finish_reason":"stop"，若是則跳出迴圈
                if (line.contains("\"finish_reason\":") && !line.contains("\"finish_reason\":null")) {
                    break;
                }

                String content;
                // 取得回傳的內容，並印出
                content = getContent(line);
                tempinput += content;
                this.output.put(sessionID,tempinput);   // 新的內容會覆蓋舊的內容

            }

            // 處理所有資料並回傳google map
            getLocation(sessionID,output.get(sessionID));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getOutput(String sessionID) {
            return this.output.get(sessionID);
    }

    public Map<String, String> getOutput() {
        return this.output;
    }


    // TODO:這邊要改成set MAP
//    public void setOutput(Map<String, String> output) {
//        this.output = output;
//    }

    // 從回傳的 JSON 格式資料中取得 content 欄位
    private String getContent(String response) {
        String[] parts = response.split("\"delta\":");
        if (parts.length < 2) {
            return "";
        }

        int start = parts[1].indexOf("\"content\":\"") + "\"content\":\"".length();
        int end = parts[1].indexOf("\"}", start);
        return parts[1].substring(start, end);
    }

    private void getLocation(String sessionID, String input) {
        Pattern pattern = Pattern.compile("地點名稱[：:](.*?)\\\\n");
        Matcher matcher = pattern.matcher(input);
        String destination = destinationInput.get(sessionID);  // FIXME:這邊要修
        locations.put(sessionID, new ArrayList<>());

        while (matcher.find()) {
            String[] strings;
            String location = matcher.group(1);
            if (location.contains("、")) {
                strings = location.split("、");
                for (var s : strings) {
                    locations.get(sessionID).add(destination+s);
                }
            } else if(location.contains("，")) {
                strings = location.split("，");
                for (var s : strings) {
                    locations.get(sessionID).add(destination+s);
                }
            }else if(location.contains(",")){
                strings = location.split(",");
                for (var s : strings) {
                    locations.get(sessionID).add(destination+s);
                }
            }else {
                strings = location.split(",");
                for (var s : strings) {
                    locations.get(sessionID).add(destination+s);
                }
            }
        }
    }

    public ArrayList<AiLocationsDto> getLatitudeAndLongitude(String memberId) {
        var locations = this.locations.get(memberId);
        // 將地點轉成經緯度 如果為空陣列，就不要執行了
        if (locations != null && !locations.isEmpty()) {
            getLocation.start(memberId, locations);
            return getLocation.locations.get(memberId);
        }
        logger.warn("地點陣列為空，無法轉換為經緯度");
        return null;
    }
}

