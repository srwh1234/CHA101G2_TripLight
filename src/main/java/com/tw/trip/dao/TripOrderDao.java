package com.tw.trip.dao;

import com.tw.trip.pojo.TripOrder;

import java.util.List;

public interface TripOrderDao {
    public void insert(TripOrder tripOrder);
    public void update(TripOrder tripOrder);
    public void deleteById(Integer id);
    public TripOrder findByPrimaryKey(Integer id);
    public List<TripOrder> getAll();

}
