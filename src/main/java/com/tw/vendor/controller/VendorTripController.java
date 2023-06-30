package com.tw.vendor.controller;



import com.tw.trip.service.TripService;
import com.tw.vendor.dto.TripManagementDto;
import com.tw.vendor.model.Trip2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorTripController {

    @Autowired
    private TripService tripService;

    @GetMapping("/trips")  // 對應前端get,  傳資料給前端
    public List<TripManagementDto> getTrips() {
        var tripManagementDtoArrayList = new ArrayList<TripManagementDto>();
        tripService.findAll().forEach(trip -> {tripManagementDtoArrayList.add(new TripManagementDto(trip));});
        return tripManagementDtoArrayList;
    }


    // TODO:setStatus有問題
    @PutMapping ("/trips1/{tripId}")
    public String enableVendor(@PathVariable int tripId){
        Trip2 trip = tripService.findById(tripId);
        trip.setStatus((byte) 1);
        tripService.save(trip);
        return "行程啟用成功";
    }
    // 更改狀態- 停權
    @PutMapping ("/trips2/{tripId}")
    public String suspensionVendor(@PathVariable int tripId){
        Trip2 trip = tripService.findById(tripId);
        trip.setStatus((byte) 2);
        tripService.save(trip);
        return "行程停權成功";
    }

}
