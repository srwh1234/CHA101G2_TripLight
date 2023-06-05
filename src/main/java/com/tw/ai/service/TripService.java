package com.tw.ai.service;


import com.tw.ai.dto.TripDto;

import com.tw.trip.model.TripImage;
import com.tw.trip.repository.TripRepository;
import com.tw.trip.repository.TripImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    // 找出指定圖片 沒有則回傳預設圖片
    public byte[] findImg(final int id) {
        var tripImage = tripImageRepository.findById(id);
        // TripImage::getImage：引用TripImage的getImage()
        // map (裡面對象如果存在則執行)
        return tripImage.map(TripImage::getImage).orElse(null);
    }

    public List<TripDto> getTrip(String destination){
        var tripDtoArrayList = new ArrayList<TripDto>();
        tripRepository.findByCityContaining(destination).forEach(trip -> tripDtoArrayList.add(new TripDto(trip)));
        return tripDtoArrayList;
    }
}
