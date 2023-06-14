package com.tw.trip.controller;


import com.tw.trip.model.dao.TripOrderDao;
import com.tw.trip.model.pojo.TripOrder;
import com.tw.trip.service.TripOrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripOrderController {

    @Autowired
    TripOrderDao tripOrderDao;

    @Autowired
    TripOrderService tripOrderService;

    @PostMapping("/order")
    public void order(@RequestParam Integer tourGroupId, @RequestParam String travelerName, @RequestParam String travelerPhone,
                      @RequestParam String travelerEmail, @RequestParam Integer travelerGender,@RequestParam Integer travelerAge,
                      HttpSession session){

        tripOrderService.addTourGroupDetail(tourGroupId, travelerName, travelerPhone,
                travelerEmail, travelerGender,travelerAge);


    }

}
