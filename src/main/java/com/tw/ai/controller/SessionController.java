package com.tw.ai.controller;

import com.tw.ai.dto.IdDto;
import com.tw.ai.dto.AiFormDataDto;
import com.tw.ai.service.AiService;
import com.tw.ai.service.ChatGPTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SessionController {
    private final AiService aiService;

    private final ChatGPTService chatGPTService;

    @Autowired
    public SessionController(AiService aiService,ChatGPTService chatGPTService) {
        this.aiService = aiService;
        this.chatGPTService = chatGPTService;
    }

    // 接收表單資料
    @PostMapping("/formData")
    public ResponseEntity<String> processFormData(@RequestBody AiFormDataDto formDara, HttpServletRequest request){
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        aiService.setFormDataList(sessionId,formDara);
        chatGPTService.start(sessionId,formDara);
        return ResponseEntity.ok("get FormData");
    }

    // 發送sessionID與表單id給進入表單頁面的使用者
    @GetMapping("/sessionId")
    public ResponseEntity<IdDto> getSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        var id = new IdDto(aiService.getFormId(),sessionId);
        return ResponseEntity.ok(id);
    }
}
