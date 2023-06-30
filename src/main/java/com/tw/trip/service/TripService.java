package com.tw.trip.service;


import com.tw.ai.dto.TripDto;

import com.tw.trip.pojo.Trip;
import com.tw.trip.pojo.TripImage;
import com.tw.trip.repository.TripRepository;
import com.tw.trip.repository.TripImageRepository;
import com.tw.vendor.dao.TripRepository2;
import com.tw.vendor.model.Trip2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripService{
    private final TripRepository2 tripRepository2;
    private final TripImageRepository tripImageRepository;

    @Autowired
    public TripService(TripRepository2 tripRepository2, TripImageRepository tripImageRepository) {
        this.tripRepository2 = tripRepository2;
        this.tripImageRepository = tripImageRepository;
    }

    // 獲得前8個熱門行程
    public List<TripDto> getHotTrip() {
        var tripDtoArrayList = new ArrayList<TripDto>();

        // Pageable 是 Spring Data 提供的一個介面，用於支持分頁和排序的查詢操作。
        Pageable pageable = PageRequest.of(0, 8); // 設置只返回前8個結果
        tripRepository2.findAllByOrderByTotalSalesDesc(pageable).forEach(trip -> tripDtoArrayList.add(new TripDto(trip)));
        return tripDtoArrayList;
    }


    // 提供img id, 得到Image 的 byte陣列
    public byte[] findImg(final int id) {
        var tripImage = tripImageRepository.findById(id);
        // TripImage::getImage：引用TripImage的getImage()
        // map (裡面對象如果存在則執行)
        return tripImage.map(TripImage::getImage).orElse(null);
    }
    // 提供地點名稱，獲得包含該地點名稱的行程
    public List<TripDto> getTrip(String destination){
        var tripDtoArrayList = new ArrayList<TripDto>();
        tripRepository2.findByCityContaining(destination).forEach(trip -> tripDtoArrayList.add(new TripDto(trip)));
        return tripDtoArrayList;
    }


    // 提供id 獲得trip
    public Trip2 findById(int tripId){
        return tripRepository2.findById(tripId).orElse(null);
    }

    public void save(Trip2 trip){
        tripRepository2.save(trip);
    }

    public List<Trip2> findAll(){
        return tripRepository2.findAll();
    }
}
