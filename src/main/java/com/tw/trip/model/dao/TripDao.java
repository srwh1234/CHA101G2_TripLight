package com.tw.trip.model.dao;

import com.tw.trip.model.pojo.Trip;

import java.util.List;


public interface TripDao {
    public void insert(Trip trip);
    public void update(Trip trip);
    public void deleteById(Integer id);
    public Trip findByPrimaryKey(Integer id);
    public List<Trip> getAll();

}
