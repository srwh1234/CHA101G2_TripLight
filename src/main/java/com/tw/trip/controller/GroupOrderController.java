package com.tw.trip.controller;

import com.tw.trip.service.GroupOrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;

@RestController
@RequestMapping("/groupOrder")
public class GroupOrderController {
//
//    @PostMapping("/addTripComments")
//    public void addTripComment(HttpServletRequest request) throws IOException {
//
//        BufferedReader reader = request.getReader();
//        StringBuilder stringBuilder = new StringBuilder();
//        String dataRead;
//
//        // ====== 1. retrieved data from request n store in stringBuilder ======
//        while((dataRead=reader.readLine()) != null){
//            stringBuilder.append(dataRead);
//        }
//
//        reader.close();
//
//        // ====== 2. parse to JSON via JSONObject ======
//        JSONObject jsonObject;
//        String param = null;
//
//        try{
//            jsonObject = new JSONObject(stringBuilder.toString());  // arguments for JSONObject is String
//            param = jsonObject.getString("message");
//
//        }catch (JSONException e){
//            e.printStackTrace();
//
//        }
//
//        // ====== 3. Sent data to DB ======
//        GroupOrderService tripCommentService = new GroupOrderService();
//        tripCommentService.addTripComment(2, 3, 1,
//                param, 2, 3);
//
//        System.out.println("update successfully!");
//
//    }
}
