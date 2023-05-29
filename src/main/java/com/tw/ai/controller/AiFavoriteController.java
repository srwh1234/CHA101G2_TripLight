package com.tw.ai.controller;

import com.google.gson.Gson;
import com.tw.ai.entity.aIFavorite.AiFavorite;
import com.tw.ai.service.aiService.AiService;
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
    @ResponseBody
    public String getAiFavorite(@PathVariable("memberId") String memberId){
        List<AiFavorite> result = aiService.findAIFavoriteFromMemberId(5);
        System.out.println(memberId);
        var gson = new Gson();
        return gson.toJson(result);
    }
    // 存入資料庫
    @PostMapping("/processResultData/{memberId}")
    @ResponseBody
    public String processResultData(@RequestParam("resultData") String resultData, @RequestParam("resultUrl") String resultUrl, @PathVariable("memberId") String memberId) {
        int aiFavoriteId = aiService.save(resultData, resultUrl, memberId);
        aiService.saveLocation(memberId, aiFavoriteId);
        return "success";
    }
}
