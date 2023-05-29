package com.tw.ai.controller;

import com.tw.ai.service.aiService.AiService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SessionController {
    private final AiService aiService;

    @Autowired
    public SessionController(AiService theaiService) {
        aiService = theaiService;
    }

    // 接收表單資料
    @PostMapping("/processVariable")
    @ResponseBody
    public String processVariable(@RequestParam("variable") String variable, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        aiService.setFormData(sessionId,variable);
        aiService.startChatGPT(sessionId, variable);
        return "success";
    }

    // 發送sessionID給進入表單頁面的使用者
    @GetMapping("/getSessionId")
    @ResponseBody
    public String getSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        System.out.println("以下成員加入網頁: " + sessionId);
        return sessionId;
    }
}
