package com.tw.trip.controller;

import com.google.gson.Gson;
import com.tw.member.model.Member;
import com.tw.trip.pojo.TourGroup;
import com.tw.trip.pojo.TripOrder;
import com.tw.trip.service.GroupOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/groupOrderDetail")
public class GroupOrderDetailController {

    @Autowired
    GroupOrderDetailService groupOrderDetailService;

    @GetMapping("/getTripInfor")
    public String getTripInfor(@RequestParam String tripOrderIdString){

        Integer tripOrderId = Integer.valueOf(tripOrderIdString);
        List<TripOrder> tripOrderList = groupOrderDetailService.getOrderInfor(tripOrderId);

        String json = new Gson().toJson(tripOrderList);

        return json;

    }

    @GetMapping("/getMemberInfor")
    public String getMemberInfor(@RequestParam String memberIdString){

        Integer memberId = Integer.valueOf(memberIdString);
        Member member = groupOrderDetailService.getMemberInfor(memberId);

        String json = new Gson().toJson(member);

        return json;

    }

    @GetMapping("/getTourGroupDate")
    public String getTourGroupDate(@RequestParam String tripIdString,
                                   @RequestParam String tourGroupIdString){

        Integer tripId = Integer.valueOf(tripIdString);
        Integer tourGroupId = Integer.valueOf(tourGroupIdString);
        TourGroup tourGroup = groupOrderDetailService.getTourGroupDate(tripId,tourGroupId);

        String json = new Gson().toJson(tourGroup);

        return json;

    }

}
