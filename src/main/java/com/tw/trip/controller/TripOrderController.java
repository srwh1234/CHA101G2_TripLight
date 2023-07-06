package com.tw.trip.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tw.member.model.Member;
import com.tw.ticket.Config;
import com.tw.ticket.MyUtils;
import com.tw.ticket.thirdparty.ecpay.payment.integration.AllInOne;
import com.tw.ticket.thirdparty.ecpay.payment.integration.domain.AioCheckOutALL;
import com.tw.trip.dao.TripOrderDao;
import com.tw.trip.pojo.TourGroup;
import com.tw.trip.pojo.TourGroupDetail;
import com.tw.trip.service.TripOrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Map;
import java.util.UUID;

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

            //System.out.println("會員編號"+jsonObject.getInt("memberId"));
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

//    @PostMapping("/updatePaymentStatus")
//    public void updatePaymentStatus(HttpServletRequest request) throws IOException{
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
//        try{
//            jsonObject = new JSONObject(stringBuilder.toString());  // arguments for JSONObject is String
//            tripOrderService.updatePaymentStatus(
//                    jsonObject.getInt("paymentStatus"),
//                    jsonObject.getInt("tripOrderId")
//            );
//
//
//        }catch (JSONException e){
//            e.printStackTrace();
//
//        }
//
//    }

    @Autowired
    private Config config;

    @PostMapping("/updatePaymentStatus")
    @ResponseBody
    public String ecpayCheckout(@RequestBody final Map<String, Object> map) {
        return getAllInOnePage((String) map.get("tripOrderId"));
    }

    /**
     * 綠界回傳的結帳結果 ( map stores the result of the EC Pay situation
     * XXX 需要Https才收的到callback <---------------- https 可用ngrok測試
     * XXX map內的參數有很多，return the infor in
     */
    @PostMapping("/tripCallback")
    public ResponseEntity<String> handleCallback(@RequestParam final Map<String, String> map) {
        final int tripOrderId = Integer.parseInt(map.get("CustomField1"));
        final int rtnCode = Integer.parseInt(map.get("RtnCode"));
        final String payType = map.get("PaymentType");

        //System.out.println(map);
        // 結帳
        if (rtnCode == 1) {
            tripOrderService.updatePaymentStatus(rtnCode, tripOrderId);
        }
        return ResponseEntity.ok("OK");
    }

    /**
     * 綠界付款介面的初始化 config.getEcpayReturnUrl() 在application.properties
     *
     * @return
     */
    private String getAllInOnePage(final String tripOrderId) {

        Integer tripOrderIdInt = Integer.parseInt(tripOrderId);
        Integer amount = tripOrderService.getTripOrderAmount(tripOrderIdInt);
        final String itemName = "TripLight訂單";

        final String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
        // UUID for occur random number

        final AioCheckOutALL obj = new AioCheckOutALL();
        obj.setMerchantTradeNo(uuId);
        obj.setMerchantTradeDate(MyUtils.getNowDateTimeString());
        obj.setTotalAmount(String.valueOf(amount));
        obj.setTradeDesc("test Description");
        obj.setItemName(itemName);
        obj.setReturnURL(config.getEcpayReturnUrl() + "/tripCallback");
        obj.setClientBackURL(config.getEcpayReturnUrl() + "/front-end/index.html");

        // 參數傳遞
        obj.setCustomField1(String.valueOf(tripOrderId));
        obj.setNeedExtraPaidInfo("N");
        return new AllInOne("").aioCheckOut(obj, null);
    }


}
