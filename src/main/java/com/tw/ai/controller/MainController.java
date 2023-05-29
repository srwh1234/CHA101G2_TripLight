package com.tw.ai.controller;


import com.google.gson.Gson;
import com.tw.ai.entity.aIFavorite.AiFavorite;
import com.tw.ai.service.TicketService;
import com.tw.ai.service.TripService;
import com.tw.ai.service.aiService.AiService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


// 更新前存檔
@RestController
@EnableScheduling
public class MainController {
    private final TripService tripService;
    private final TicketService ticketService;
    private final AiService aiService;

    @Autowired
    public MainController(TripService thetripService,AiService theaiService,TicketService ticketService) {
        // 創建物件
        tripService = thetripService;
        aiService = theaiService;
        this.ticketService = ticketService;
    }

    // 接收表單資料
    @PostMapping("/processVariable")
    @ResponseBody
    public String processVariable(@RequestParam("variable") String variable, HttpServletRequest request) {
        // 獲得session ID
        HttpSession session = request.getSession();
        String sessionId = session.getId();

        // 紀錄表單資料
        aiService.setFormData(sessionId,variable);

        // 使用表單資料執行ChatGPTAPI
        aiService.startChatGPT(sessionId, variable);

        return "success";
    }

    // 發送sessionID給進入表單頁面的使用者
    @GetMapping("/getSessionId")
    @ResponseBody
    public String getSessionId(HttpServletRequest request) {
        // 獲得session ID
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        System.out.println("以下成員加入網頁: " + sessionId);
        return sessionId;
    }

    // 將行程內容傳至前端  表單送出時呼叫
    @GetMapping(value = "/sse/{memberId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sse(@PathVariable("memberId") String memberId) {
        return Flux.interval(Duration.ofMillis(100)) // 每隔0.1秒發送一個 SSE 事件
                    //保證每個事件都會是一個非空字串
                .map(ignore -> "" + aiService.getChatGPTResult(memberId)) // 格式化為 SSE 事件格式
                .distinctUntilChanged(); // 過濾掉與前一個事件相同的事件
    }

    // 將緯度資料傳至前端  在行程傳輸完畢時呼叫
    @GetMapping("/getArray/{memberId}")
    @ResponseBody
    public String getArray(@PathVariable("memberId") String memberId) {
        return aiService.getLatitudeAndLongitude(memberId);
    }


    // 將地點資料傳至前端  在行程傳輸完畢呼叫
    @GetMapping("/getLocations/{memberId}")
    @ResponseBody
    public String getLocations(@PathVariable("memberId") String memberId) {
        ArrayList<String> result = aiService.getChatGPTLocations(memberId);
        var gson = new Gson();
        return gson.toJson(result);
    }

    // 將推薦行程傳至前端  在表單送出時呼叫
    @GetMapping("/getPackages/{memberId}")
    @ResponseBody
    public String  getPackages(@PathVariable("memberId") String memberId){
        var destination = aiService.getDestination(memberId);
        return tripService.getTrip(destination);
    }


    // 將推薦票券傳至前端  在表單送出時呼叫
    @GetMapping("/getTickets/{memberId}")
    @ResponseBody
    public String getTickets(@PathVariable("memberId") String memberId){
        var destination = aiService.getDestination(memberId);
        return ticketService.getTicket(destination);
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
        // 存入AiFavorite
        int  aiFavoriteId = aiService.save(resultData,resultUrl,memberId);

        // 存入 AiLocations
        aiService.saveLocation(memberId,aiFavoriteId);


        return "success";
    }

    // 判斷用戶是否離開該網頁
    @PostMapping("/notifyBackend/{memberId}")
    public void handleNotification(@PathVariable("memberId") String memberId) {
        System.out.println("以下成員離開網頁，成員ID： " + memberId+"執行清空作業");
        // 清空相關資料
        // TODO:清空地點資料，清空行程資料
        aiService.clearContent(memberId);    //TODO:清空地點資料
    }

    // 每30秒接收1次訊息, 更新使用者的最後活動時間
    @PostMapping("/heartbeat/{memberId}")
    public void handleHeartbeat(@PathVariable("memberId") String memberId) {
        aiService.updateHeartbeat(memberId);
    }


    // 每分鐘檢查所有連線
    @Scheduled(fixedRate = 10000) // 每分鐘
    public void checkHeartbeat() {
        aiService.checkHeartbeat();
    }
}

