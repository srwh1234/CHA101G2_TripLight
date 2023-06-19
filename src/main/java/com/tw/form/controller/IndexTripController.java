package com.tw.form.controller;


import com.tw.ai.dto.TripDto;
import com.tw.trip.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexTripController {

    private TripService tripService;

    @Autowired
    public IndexTripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/index/trips")
    public List<TripDto> getTrip(){
        var hotTrip = tripService.getHotTrip();
        return hotTrip;
    }
}
