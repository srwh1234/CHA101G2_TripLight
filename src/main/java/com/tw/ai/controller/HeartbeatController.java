package com.tw.ai.controller;

import com.tw.ai.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableScheduling
public class HeartbeatController {
    private final AiService aiService;

    @Autowired
    public HeartbeatController(AiService theaiService) {
        aiService = theaiService;
    }

    // 判斷用戶是否離開該網頁
    @PostMapping("/notifyBackend/{memberId}")
    public void handleNotification(@PathVariable("memberId") String memberId) {
        aiService.clearContent(memberId);
    }
    // 每30秒接收1次訊息, 更新使用者的最後活動時間
    @PostMapping("/heartbeat/{memberId}")
    public void handleHeartbeat(@PathVariable("memberId") String memberId) {
        aiService.updateHeartbeat(memberId);
    }
    // 每分鐘檢查所有連線
    @Scheduled(fixedRate = 10000)
    public void checkHeartbeat() {
        aiService.checkHeartbeat();
    }
}
