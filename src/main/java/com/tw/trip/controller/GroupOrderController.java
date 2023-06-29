package com.tw.trip.controller;

import com.google.gson.Gson;
import com.tw.member.model.Member;
import com.tw.trip.pojo.Trip;
import com.tw.trip.service.GroupOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groupOrder")
public class GroupOrderController {

    @Autowired
    GroupOrderService groupOrderService;

    @GetMapping("/getMember")
    public String getMemberByMemberId(@RequestParam Integer memberId){
        Member member = groupOrderService.getMemberInfor(memberId);

        String json = new Gson().toJson(member);

        return json;
    }

    @GetMapping("/getTrips")
    public String getTripsByMemberId(@RequestParam Integer memberId){
        List<Trip> tripList = groupOrderService.getTripsByMemberId(memberId);

        String json = new Gson().toJson(tripList);

        return json;
    }


}
