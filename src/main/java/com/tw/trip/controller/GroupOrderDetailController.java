package com.tw.trip.controller;

import com.google.gson.Gson;
import com.tw.member.model.Member;
import com.tw.trip.pojo.TourGroup;
import com.tw.trip.pojo.TourGroupDetail;
import com.tw.trip.pojo.TripOrder;
import com.tw.trip.service.GroupOrderDetailService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/groupOrderDetail")
public class GroupOrderDetailController {

    @Autowired
    GroupOrderDetailService groupOrderDetailService;

    @GetMapping("/getTripInfor")
    public String getTripInfor(@RequestParam Integer tripOrderId){

        List<TripOrder> tripOrderList = groupOrderDetailService.getOrderInfor(tripOrderId);

        String json = new Gson().toJson(tripOrderList);

        return json;

    }

    @GetMapping("/getMemberInfor")
    public String getMemberInfor(@RequestParam Integer memberId){

        Member member = groupOrderDetailService.getMemberInfor(memberId);

        String json = new Gson().toJson(member);

        return json;

    }

    @GetMapping("/getTourGroupDate")
    public String getTourGroupDate(@RequestParam Integer tripId,
                                   @RequestParam Integer tourGroupId){

        TourGroup tourGroup = groupOrderDetailService.getTourGroupDate(tripId,tourGroupId);

        String json = new Gson().toJson(tourGroup);

        return json;

    }

    @GetMapping("/getTravelerList")
    public String getTravelerList(@RequestParam Integer memberId,
                                  @RequestParam Integer tourGroupId){

        List<TourGroupDetail> tourGroupDetails = groupOrderDetailService.getTravelerList(memberId,tourGroupId);

        String json = new Gson().toJson(tourGroupDetails);
        return json;
    }

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
        try{
            jsonObject = new JSONObject(stringBuilder.toString());  // arguments for JSONObject is String
            groupOrderDetailService.addTripComment(
                    jsonObject.getInt("tripId"),
                    jsonObject.getInt("memberId"),
                    jsonObject.getInt("rating"),
                    jsonObject.getString("comment"),
                    jsonObject.getInt("status"),
                    jsonObject.getInt("edit_count"),
                    jsonObject.getInt("tripOrderId")
            );

        }catch (JSONException e){
            e.printStackTrace();

        }

        //System.out.println("TripComment successfully inserted!");

    }

    @GetMapping("/getEditCount")
    public Integer getEditCount(@RequestParam Integer tripOrderId){

        Integer editCount = groupOrderDetailService.getTripCommentEditCount(tripOrderId);
        return editCount;
    }


}
