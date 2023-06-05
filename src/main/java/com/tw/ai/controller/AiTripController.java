package com.tw.ai.controller;

import com.tw.ai.service.TripService;
import com.tw.ai.service.AiService;
import com.tw.trip.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AiTripController {
    private final TripService tripService;
    private final AiService aiService;

    @Autowired
    public AiTripController(TripService thetripService, AiService theaiService) {
        tripService = thetripService;
        aiService = theaiService;
    }
    // 將推薦行程傳至前端  在表單送出時呼叫
    @GetMapping("/trips/{memberId}")
    public List<Trip> getPackages(@PathVariable("memberId") String memberId) {
        var destination = aiService.getDestination(memberId);
        return tripService.getTrip(destination);
    }
}
