package com.tw.ai.controller;

import com.tw.ai.dto.IdDto;
import com.tw.ai.dto.AiFormDataDto;
import com.tw.ai.service.AiService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SessionController {
    private final AiService aiService;

    @Autowired
    public SessionController(AiService theaiService) {
        aiService = theaiService;
    }

    // 接收表單資料
    @PostMapping("/processFormData")
    public ResponseEntity<String> processFormData(@RequestBody AiFormDataDto formDara, HttpServletRequest request){
        HttpSession session = request.getSession();
        String sessionId = session.getId();

        System.out.println(formDara);

        aiService.setFormDataList(sessionId,formDara);

        aiService.startChatGPT(sessionId,formDara);

        return ResponseEntity.ok("get FormData");
    }

    // 發送sessionID與表單id給進入表單頁面的使用者
    @GetMapping("/getSessionId")
    public ResponseEntity<IdDto> getSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionId = session.getId();

        var id = new IdDto(aiService.getFormId(),sessionId);
        return ResponseEntity.ok(id);
    }
}
