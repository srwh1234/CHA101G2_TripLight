package com.tw.ai.controller;

import com.tw.ai.dto.AiLocationsDto;
import com.tw.ai.service.AiService;
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
    private final AiService aiService;

    @Autowired
    public ChatGPTController(AiService theaiService) {
        aiService = theaiService;
    }
    // 將行程內容傳至前端  表單送出時呼叫
    @GetMapping(value = "/sse/{memberId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sse(@PathVariable("memberId") String memberId) {
        return Flux.interval(Duration.ofMillis(100))
                .map(ignore -> "" + aiService.getChatGPTResult(memberId))
                .distinctUntilChanged();
    }
    // 將緯度資料傳至前端  在行程傳輸完畢時呼叫
    @GetMapping("/getArray/{memberId}")
    public ArrayList<AiLocationsDto> getArray(@PathVariable("memberId") String memberId) {
        return aiService.getLatitudeAndLongitude(memberId);
    }
    // 將地點資料傳至前端  在行程傳輸完畢呼叫
    @GetMapping("/getLocations/{memberId}")
    public ArrayList<String> getLocations(@PathVariable("memberId") String memberId) {
        return aiService.getChatGPTLocations(memberId);
    }
}
