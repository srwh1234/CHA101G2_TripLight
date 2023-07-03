package com.tw.ai.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tw.ai.config.AppConfig;
import com.tw.ai.dto.AiLocationsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GetLocation {

    public Map<String,ArrayList<AiLocationsDto>> locations = new ConcurrentHashMap<>();

    public String content;
    private String output;

    private ArrayList<String> locationTitle = new ArrayList<>();

    private final String API_KEY;
    private static final String MODEL = "gpt-3.5-turbo";

    private static final int MAX_TOKENS = 2500; // 設定每次請求的字數上限 (一個中文字2Token, 一個英文字1Token)
    private static final String URL = "https://api.openai.com/v1/chat/completions";

    @Autowired
    public GetLocation(AppConfig appConfig) {
        this.API_KEY = appConfig.getApiKey();
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
    private String createJSONPayload(String message) throws JsonProcessingException, UnsupportedEncodingException {
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
        outputStream.write(data.getBytes());

        // 強制將緩衝區資料寫入
        outputStream.flush();
    }

    // 獲取響應
    public BufferedReader getResponse(HttpURLConnection connection) throws IOException {
        // 獲取與連線相關輸入流，這是從伺服器接收資料的通道
        InputStream inputStream = connection.getInputStream();

        // 將位元輸入流轉為字元輸入流，使用 UTF-8 編碼
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        // 將字元輸入流添加緩衝，減少I/O次數
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        // 回傳輸入流，給呼叫的method自行處理
        return bufferedReader;
    }

    //  ======================================== 串接API ========================================


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


