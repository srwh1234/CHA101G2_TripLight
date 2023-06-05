package com.tw.ai.controller;


import com.tw.ai.model.AiFavorite;
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

    @GetMapping("/aiFavorite/{memberId}")
    public List<AiFavorite> getAiFavorite(@PathVariable("memberId") int memberId){
        var aiFavoriteFromMemberId = aiService.findAIFavoriteFromMemberId(memberId);
        if(aiFavoriteFromMemberId.size() == 0){
            return null;
        }else {
            return aiService.findAIFavoriteFromMemberId(memberId);
        }
    }
    // 存入資料庫
    @PostMapping("/aiFavorite/{memberId}")
    public String processResultData(@RequestParam("resultData") String resultData, @RequestParam("resultUrl") String resultUrl, @PathVariable("memberId") String memberId) {
        aiService.save(resultData, resultUrl, memberId);
//        aiService.saveLocation(memberId, aiFavoriteId);
        return "儲存成功";
    }
}
