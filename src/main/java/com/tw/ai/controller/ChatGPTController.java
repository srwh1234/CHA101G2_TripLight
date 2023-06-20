package com.tw.ai.controller;

import com.tw.ai.dto.AiLocationsDto;
import com.tw.ai.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;

@RestController
public class ChatGPTController {
    private final ChatGPTService chatGPTService;

    @Autowired
    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }
    // 將行程內容傳至前端  表單送出時呼叫
    // 這段程式碼是一個使用Spring WebFlux框架實現的Server-Sent Events（SSE）端點。SSE是一種基於HTTP的輕量級通訊協議，它允許服務器向客戶端推送持續的資料流。
    @GetMapping(value = "/sse/{memberId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sse(@PathVariable("memberId") String memberId) {
        return Flux.interval(Duration.ofMillis(100))
                .map(ignore -> "" + chatGPTService.getOutput(memberId))
                .distinctUntilChanged();
    }
    // 將緯度資料傳至前端  在行程傳輸完畢時呼叫
    @GetMapping("/longitude/{memberId}")
    public ArrayList<AiLocationsDto> getLongitude(@PathVariable("memberId") String memberId) {
        return chatGPTService.getLatitudeAndLongitude(memberId);
    }
    // 將地點資料傳至前端  在行程傳輸完畢呼叫
    @GetMapping("/locations/{memberId}")
    public ArrayList<String> getLocations(@PathVariable("memberId") String memberId) {
        return chatGPTService.locations.get(memberId);
    }
}
