package com.tw.ai.service;


import com.tw.ai.util.DateUtils;

import com.tw.trip.dao.TripRepository;
import com.tw.trip.dao.TripImageRepository;
import com.tw.trip.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService{


    private final TripRepository tripRepository;
    private final TripImageRepository tripImageRepository;

    @Autowired
    public TripService(TripRepository tripRepository, TripImageRepository tripImageRepository) {
        this.tripRepository = tripRepository;
        this.tripImageRepository = tripImageRepository;
    }

    public List<Trip> getTrip(String destination){
        List<Trip> result = tripRepository.findByCityContaining(destination);
        for(var trip:result){
            var tripImg = tripImageRepository.findByTripId(1);  // TODO:這邊要改
            byte[] image = tripImg.get(0).getImage();  // 獲得圖片byte[]
            var url = DateUtils.convertBlobToUrl(image);
            // 將圖片byte轉成url
            trip.setImgUrl(url);
        }
        return result;
    }


}
