package com.tw.vendor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tw.vendor.model.Trip2;

@Service
public interface TripService2 {

    public void save(Trip2 trip);

    public List<Trip2> findAll();

    public void update(Trip2 trip);

    public Trip2 findById(int tripId);

//    public void deleteById(int tripId);

}