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

    // ====== Group Order 新增評論 ======
    @PostMapping("/addTripComments")
    public void addTripComment(HttpServletRequest request) throws IOException {

        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String dataRead;

        // ====== 1. retrieved data from request n store in stringBuilder ======
        while((dataRead=reader.readLine()) != null){
            stringBuilder.append(dataRead);
        }

        reader.close();

        // ====== 2. parse to JSON via JSONObject ======
        JSONObject jsonObject;
        String param = null;

        try{
            jsonObject = new JSONObject(stringBuilder.toString());  // arguments for JSONObject is String
            param = jsonObject.getString("message");

        }catch (JSONException e){
            e.printStackTrace();

        }

        // ====== 3. Sent data to DB ======
        groupOrderService.addTripComment(2, 3, 1,
                param, 2, 3);

        System.out.println("update successfully!");

    }


    @GetMapping("/getTrips")
    public String getTripsByMemberId(@RequestParam String memberIdString){
        List<GroupOrderObj> groupOrderObjList = groupOrderService.getTripsByMemberId(Integer.valueOf(memberIdString));

        String json = new Gson().toJson(groupOrderObjList);

        return json;
    }
}
