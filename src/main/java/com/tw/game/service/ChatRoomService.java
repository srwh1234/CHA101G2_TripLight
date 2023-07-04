package com.tw.game.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


@Service
@SessionScope
public class ChatRoomService {

    // 設定GPT API 基本參數
    private static final String MODEL = "gpt-3.5-turbo"; // 設定模型名稱
    private static final int MAX_TOKENS = 2500; // 設定每次請求的字數上限 (一個中文字2Token, 一個英文字1Token)
    private static final String URL = "https://api.openai.com/v1/chat/completions"; // ChatGPT API 的 URL

    @Value("${API_KEY}")
    private String API_KEY; // 你的 API 密鑰

    private final Logger logger
            = LoggerFactory.getLogger(this.getClass());

    // 創建 Flux 以發送數據
    private FluxSink<String> outputSink;
    private Flux<String> outputFlux = Flux.create(sink -> outputSink = sink);

    //  ======================================== 串接API ========================================
    // 建立連線，回傳BufferedReader
    public BufferedReader getResponseFromChatGPT(String message) throws IOException {
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
    private String createJSONPayload(String message) throws JsonProcessingException{
        // 建立 Object mapper
        ObjectMapper objectMapper = new ObjectMapper();

        // 使用 objectMapper 建立一個 ObjectNode
        ObjectNode objectNode = objectMapper.createObjectNode();

        // 添加屬性，分別為模型設定，token 數量（max_tokens），是否進行流傳輸（stream）
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

        // 將字串轉為位元，並將位元資料寫入輸出流，備註資料為編碼為UTF-8
        outputStream.write(data.getBytes("UTF-8"));

        // 強制將緩衝區資料寫入
        outputStream.flush();
    }

    // 獲取響應
    public BufferedReader getResponse(HttpURLConnection connection) throws IOException {
        // 獲取與連線相關輸入流，這是從伺服器接收資料的通道
        InputStream inputStream = connection.getInputStream();

        // 將位元輸入流轉為字元輸入流，使用 UTF-8 編碼
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");

        // 將字元輸入流添加緩衝，減少 I/O 次數
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        // 回傳輸入流，給呼叫的 method 自行處理
        return bufferedReader;
    }



    //讀取資料
    public void readResponse(String userInput){
        // 使用 try-with-resources 確保資源正確關閉
        try (BufferedReader reader = getResponseFromChatGPT(userInput)){
            String line;
            // 使用readline() 獲取字串資料，直到沒資料
            while ((line = reader.readLine()) != null ){
                // 對讀取的資料作處理
                processLine(line);
            }
        } catch (IOException e) {
            logger.error("ChatGPT API 連線失敗");
        }
    }

    //  ======================================== 資料處理 ========================================
    // 刪掉 data: 將字串轉json
    private void processLine(String line) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 將 data:去掉
        if(line.startsWith("data:")){
            String jsonLine = line.substring(5);
            try {
                // 將字串 轉json
                JsonNode jsonNode = objectMapper.readTree(jsonLine);

                // 將json資料做進一步處理
                processJsonNode(jsonNode);
            }catch (JsonProcessingException e){
                // 針對json轉型失敗處理
                logger.error("json轉型失敗");
            }
        }
    }

    // 取得json資料，並獲取choices陣列，並遍歷其屬性(delta，index，finish_reason)
    private void processJsonNode(JsonNode jsonNode){
        // 檢查是否有choices 屬性
        if(jsonNode.has("choices")){
            // 取得choices陣列
            JsonNode choiceNode = jsonNode.get("choices");

            // 遍歷choices陣列所有元素
            if(choiceNode.isArray()){
                for(JsonNode choice:choiceNode){
                    // 針對choiceNode 做進一步處理
                    processChoicesNode(choice);
                }
            }
        }
    }

    // 檢查choices 裡面使否包含delta，並且delta 要包含content
    private void processChoicesNode(JsonNode choice) {
        // 取得content 屬性
        if(choice.has("delta")&& choice.get("delta").has("content")){
            // 将 content屬性轉為String
            String content = choice.get("delta").get("content").asText();
            content = content.replace("\n", "\\n");
            content = content.replace(" ", "\\s");
            // 将数据存入变量
            System.out.print(content);
            if (outputSink != null) {
                outputSink.next(content); // 将新内容推送到 Flux
            }
        }
    }


    public Flux<String> getOutput() {
        return this.outputFlux;
    }



    //  ======================================== 測試main ========================================
    public static void main(String[] args) {
        ChatRoomService chatRoomService = new ChatRoomService();
        chatRoomService.readResponse("hi");
        chatRoomService.getOutput();
    }
}
