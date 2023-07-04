package com.tw.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tw.ai.config.AppConfig;
import com.tw.ai.dto.AiFormDataDto;
import com.tw.ai.dto.AiLocationsDto;
import com.tw.ai.util.GetLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
    private static final String MODEL = "gpt-3.5-turbo"; // 設定模型名稱
    private static final int MAX_TOKENS = 2500; // 設定每次請求的字數上限 (一個中文字2Token, 一個英文字1Token)
    private static final String URL = "https://api.openai.com/v1/chat/completions"; // ChatGPT API 的 URL

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

    //  ======================================== 串接API ========================================
    // 建立連線，回傳BufferedReader
    public BufferedReader chatGPT(String message) throws IOException {
        // 創建json請求主體
        String data = createJSONPayload(message);

        // 建立chatGPT 連線
        HttpURLConnection connection = createConnection();

        // 發送請求
        sendRequest(connection, data);

        // 獲取回應
        return getResponse(connection);
    }


    // 創建json 請求主體
    private String createJSONPayload(String message) throws JsonProcessingException {
        // 建立Object mapper
        ObjectMapper objectMapper = new ObjectMapper();

        // 使用 objectMapper 建立一個 ObjectNode
        ObjectNode objectNode = objectMapper.createObjectNode();

        // 添加屬性, 分別為模型設定， token 數量（max_tokens），是否進行流傳輸（stream）
        objectNode.put("model", MODEL)
                .put("max_tokens", MAX_TOKENS)
                .put("stream", true);
        // 添加陣列
        objectNode.putArray("messages").addObject()
                .put("role", "user")
                .put("content", message);

        // 使用 objectMapper 將 ObjectNode 轉換為字串
        return objectMapper.writeValueAsString(objectNode);
    }

    // 建立連線
    private HttpURLConnection createConnection() throws IOException {
        // 建立url連線
        java.net.URL url = new URL(URL);

        // 建立與指定 URL 之間的連線並返回一個 URLConnection 物件
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 設定連線的請求方法
        connection.setRequestMethod("POST");

        // 設定連線的請求屬性
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY); // 注意Bearer 後面的空格

        // 設定連線是否可以向伺服器寫入資料
        connection.setDoOutput(true);

        // 回傳連線
        return connection;
    }

    // 發送請求
    private void sendRequest(HttpURLConnection connection, String data) throws IOException {
        // 獲取與連線相關的輸出流
        OutputStream outputStream = connection.getOutputStream();

        // 將字串轉為位元，並將位元資料寫入輸出流
        outputStream.write(data.getBytes("UTF-8"));

        // 強制將緩衝區資料寫入
        outputStream.flush();
    }

    // 獲取響應
    public BufferedReader getResponse(HttpURLConnection connection) throws IOException {
        // 獲取與連線相關輸入流，這是從伺服器接收資料的通道
        InputStream inputStream = connection.getInputStream();

        // 將位元輸入流轉為字元輸入流
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");

        // 將字元輸入流添加緩衝，減少I/O次數
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        // 回傳輸入流，給呼叫的method自行處理
        return bufferedReader;
    }

    //  ======================================== 串接API ========================================

    public void start(String sessionID, AiFormDataDto formData) {
        locations.clear();
        destinationInput.put(sessionID,formData.getDestination());  // 拿取資料
        String userInput = """
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

        try (BufferedReader chatReader = chatGPT(userInput)) {

            String line;

            // 處理回傳的資料
            while ((line = chatReader.readLine()) != null ) {

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
            logger.error("ChatGPT API 連線失敗");
        }
    }

    public String getOutput(String sessionID) {
            return this.output.get(sessionID);
    }

    public Map<String, String> getOutput() {
        return this.output;
    }


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

