package com.tw.trip.service;


import com.tw.ai.dto.TripDto;

import com.tw.trip.model.TripImage;
import com.tw.trip.repository.TripRepository;
import com.tw.trip.repository.TripImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // 獲得前8個熱門行程
    public List<TripDto> getHotTrip() {
        var tripDtoArrayList = new ArrayList<TripDto>();

        // Pageable 是 Spring Data 提供的一個介面，用於支持分頁和排序的查詢操作。
        Pageable pageable = PageRequest.of(0, 8); // 設置只返回前8個結果
        tripRepository.findAllByOrderByTotalSalesDesc(pageable).forEach(trip -> tripDtoArrayList.add(new TripDto(trip)));
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
        tripRepository.findByCityContaining(destination).forEach(trip -> tripDtoArrayList.add(new TripDto(trip)));
        return tripDtoArrayList;
    }
}
