package com.tw.ai.service;


import com.tw.ai.dto.TripDto;
import com.tw.ai.util.DateUtils;

import com.tw.ticket.controller.TicketController;
import com.tw.trip.model.TripImage;
import com.tw.trip.repository.TripRepository;
import com.tw.trip.repository.TripImageRepository;
import com.tw.trip.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        // 使用 get() 方法從 Optional<TripImage> 中獲取實際的 TripImage 物件
        return tripImage.get().getImage();
    }

    public List<TripDto> getTrip(String destination){
        var tripDtoArrayList = new ArrayList<TripDto>();
        tripRepository.findByCityContaining(destination).forEach(trip -> {
            tripDtoArrayList.add(new TripDto(trip));
        });
        return tripDtoArrayList;
    }
}
