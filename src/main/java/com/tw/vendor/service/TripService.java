package com.tw.vendor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.vendor.dao.TripRepository;
import com.tw.vendor.model.Trip;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    public void save(Trip trip){
				// 可以加入其他業務流程
        tripRepository.save(trip);
    }

    public List<Trip> findAll(){
				// 可以加入其他業務流程
        return tripRepository.findAll();
    }
    
}
