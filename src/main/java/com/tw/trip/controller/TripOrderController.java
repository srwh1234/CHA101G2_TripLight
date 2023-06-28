package com.tw.trip.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tw.member.model.Member;
import com.tw.trip.dao.TripOrderDao;
import com.tw.trip.pojo.TourGroup;
import com.tw.trip.pojo.TourGroupDetail;
import com.tw.trip.service.TripOrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.http.HttpRequest;

@RestController
public class TripOrderController {

    @Autowired
    TripOrderDao tripOrderDao;

    @Autowired
    TripOrderService tripOrderService;


    @PostMapping("/order")
    public void order(HttpServletRequest request) throws IOException {

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

        try{
            jsonObject = new JSONObject(stringBuilder.toString());  // arguments for JSONObject is String
            // ====== 1. deal with TripOrder ======

            System.out.println("會員編號"+jsonObject.getInt("memberId"));
            tripOrderService.addTripOrder(
                    jsonObject.getInt("memberId"),
                    jsonObject.getInt("tourGroupId"),
                    jsonObject.getInt("orderStatus"),
                    jsonObject.getString("payType"),
                    jsonObject.getInt("travelersChildren"),
                    jsonObject.getInt("travelersAdult"),
                    jsonObject.getString("remarks"));


            // ====== 2. deal with TourGroupDetail[] ======
            String tourGroupDetail = jsonObject.getString("tourGroupDetail");

            ObjectMapper objectMapper = new ObjectMapper();
            TourGroupDetail[] tourGroupDetails = objectMapper.readValue(tourGroupDetail,TourGroupDetail[].class);
            tripOrderService.addTourGroupDetail(tourGroupDetails);


        }catch (JSONException e){
            e.printStackTrace();

        }



    }

    @GetMapping("/getTourGroup")
    public String getTourGroup(@RequestParam Integer tourGroupId){
        TourGroup tourGroup = tripOrderService.getTourGroupById(tourGroupId);

        Gson gson = new Gson();
        String json = gson.toJson(tourGroup);
        return json;
    }

    @GetMapping("/getMemberInfor")
    public String getMemberInfor(@RequestParam Integer memberId){
        Member member = tripOrderService.getMemberInfor(memberId);

        String json = new Gson().toJson(member);
        return json;
    }


    @GetMapping("/getOrderAmount")
    public String getOrderAmount(@RequestParam Integer tripOrderId){

        Integer amount = tripOrderService.getTripOrderAmount(tripOrderId);

        String json = new Gson().toJson(amount);
        return json;
    }

    @PostMapping("/insertPaymentStatus")
    public void insertPaymentStatus(HttpServletRequest request) throws IOException{
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
        try{
            jsonObject = new JSONObject(stringBuilder.toString());  // arguments for JSONObject is String
            tripOrderService.insertPaymentStatus(
                    jsonObject.getInt("paymentStatus"),
                    jsonObject.getInt("tripOrderId")
            );


        }catch (JSONException e){
            e.printStackTrace();

        }

        System.out.println("paymentStatus successfully inserted!");

    }


}
