package com.tw.ai.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tw.ai.config.AppConfig;
import com.tw.ai.dto.AiLocationsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GetLocation {

    public Map<String,ArrayList<AiLocationsDto>> locations = new HashMap<>();

    public String content;
    private String output;

    private ArrayList<String> locationTitle = new ArrayList<>();

    private final String API_KEY;
    private static final String MODEL = "gpt-3.5-turbo";
    private static final String URL = "https://api.openai.com/v1/chat/completions";

    @Autowired
    public GetLocation(AppConfig appConfig) {
        this.API_KEY = appConfig.getApiKey();
    }

    // 這個方法會傳回一個 InputStream，該 InputStream 會連線到 ChatGPT API 並傳送 message
    private BufferedReader chatGPT(String message) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("model", MODEL);
        ArrayNode messagesNode = rootNode.putArray("messages");
        ObjectNode messageNode = messagesNode.addObject();
        messageNode.put("role", "user");
        messageNode.put("content", message);
        rootNode.put("max_tokens", 1000);
        rootNode.put("stream", true);
        String data = objectMapper.writeValueAsString(rootNode);

        // 連線到 ChatGPT API
        java.net.URL url = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setDoOutput(true);
        OutputStream out = connection.getOutputStream();
        out.write(data.getBytes());
        out.flush();

        // 取得回傳的 BufferedReader
        return new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }


    // 以下是各地點的經緯度：
    //
    //東京: 35.6895° N, 139.6917° E
    //高雄: 22.6273° N, 120.3014° E
    //台中: 24.1477° N, 120.6736° E
    //花蓮: 23.9769° N, 121.6044° E
    //注意，經度（Longitude）表
    public void start(String sessionId, ArrayList<String> message) {
        this.locationTitle = message;

        String message1 = """
        請將我提供的地點轉換成經緯度，以小數度（Decimal Degrees）為單位，
        [地點]:[緯度,經度]
        """ + message;
        String input = "";
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

                // 取得回傳的內容，並印出
                content = getContent(line);
                input += content;
                setOutput(input);

            }


            // 處理所有資料並回傳google map

            getLocation(sessionId, input, this.locationTitle);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
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



    private void getLocation(String sessionID ,String input, ArrayList<String> locationTitle) {

        // 先對input 做分析，檢查地點是否有° S 或 ° W
        // 如果有的話，要在前面加負號
        Pattern pattern = Pattern.compile("(-?\\d+\\.\\d+)°?\\s*([NS])?,?\\s*(-?\\d+\\.\\d+)°?\\s*([EW])?");
        Matcher matcher = pattern.matcher(input);
        locations.put(sessionID,new ArrayList<>());   // 會是全新的內容
        int i = 0;
        while (matcher.find()) {
            double latitude = Double.parseDouble(matcher.group(1));
            double longitude = Double.parseDouble(matcher.group(3));

            if (matcher.group(2) != null && matcher.group(2).equals("S")) {
                latitude *= -1;
            }

            if (matcher.group(4) != null && matcher.group(4).equals("W")) {
                longitude *= -1;
            }


            locations.get(sessionID).add(new AiLocationsDto( locationTitle.get(i),latitude,longitude));
            i++;
        }


    }

}


// TODO:先讓文字框置中，等到地圖收到資訊後顯示


