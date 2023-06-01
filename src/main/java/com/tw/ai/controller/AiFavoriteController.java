package com.tw.ai.controller;


import com.tw.ai.entity.AiFavorite;
import com.tw.ai.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AiFavoriteController {
    private final AiService aiService;

    @Autowired
    public AiFavoriteController(AiService theaiService) {
        aiService = theaiService;
    }
    // 將AI行程收藏傳至前端
    @GetMapping("/getAiFavorite/{memberId}")
    public List<AiFavorite> getAiFavorite(@PathVariable("memberId") String memberId){
        return aiService.findAIFavoriteFromMemberId(5);
    }
    // 存入資料庫
    @PostMapping("/processResultData/{memberId}")
    public String processResultData(@RequestParam("resultData") String resultData, @RequestParam("resultUrl") String resultUrl, @PathVariable("memberId") String memberId) {
        int aiFavoriteId = aiService.save(resultData, resultUrl, memberId);
        System.out.println(aiFavoriteId);
        aiService.saveLocation(memberId, aiFavoriteId);
        return "success";
    }
}
