package com.tw.trip.controller;

import com.google.gson.Gson;
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

}
