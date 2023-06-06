package com.tw.ai.controller;

import com.tw.ai.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AiFavoriteController {
    private final AiService aiService;

    @Autowired
    public AiFavoriteController(AiService theaiService) {
        aiService = theaiService;
    }

    @GetMapping("/aiFavorite/{memberId}")
    public ResponseEntity<Object> getAiFavorite(@PathVariable("memberId") int memberId) {
        var aiFavoriteFromMemberId = aiService.findAIFavoriteFromMemberId(memberId);
        if (aiFavoriteFromMemberId.isEmpty()) {
            String errorMessage = "No AI favorites found for memberId: " + memberId;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } else {
            return ResponseEntity.ok(aiFavoriteFromMemberId);
        }
    }

    // 存入資料庫
    @PostMapping("/aiFavorite/{memberId}")
    public ResponseEntity<Boolean> processResultData(@RequestParam("resultData") String resultData, @RequestParam("resultUrl") String resultUrl, @PathVariable("memberId") String memberId) {
        try {
            boolean saved = aiService.save(resultData, resultUrl, memberId);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            // 處理異常狀況
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }


}
