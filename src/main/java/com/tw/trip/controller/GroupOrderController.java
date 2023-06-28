package com.tw.trip.controller;

import com.google.gson.Gson;
import com.tw.trip.pojo.GroupOrderObj;
import com.tw.trip.service.GroupOrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/groupOrder")
public class GroupOrderController {

    @Autowired
    GroupOrderService groupOrderService;

    @GetMapping("/getTrips")
    public String getTripsByMemberId(@RequestParam String memberIdString){
        List<GroupOrderObj> groupOrderObjList = groupOrderService.getTripsByMemberId(Integer.valueOf(memberIdString));

        String json = new Gson().toJson(groupOrderObjList);

        return json;
    }


}
