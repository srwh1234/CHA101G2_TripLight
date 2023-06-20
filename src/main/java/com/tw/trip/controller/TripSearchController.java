package com.tw.trip.controller;

import com.google.gson.Gson;
import com.tw.trip.pojo.Trip;
import com.tw.trip.service.TripSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tripSearch")
public class TripSearchController {

    @Autowired
    TripSearchService tripSearchService;

    @GetMapping("/getTripList")
    public String getTripList(){
        List<Trip> tripList = tripSearchService.getTripListWithPic();
        String json = new Gson().toJson(tripList);

        return json;
    }


}
