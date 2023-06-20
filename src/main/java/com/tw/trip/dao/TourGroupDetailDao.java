package com.tw.trip.dao;

import com.tw.trip.pojo.TourGroupDetail;

import java.util.List;

public interface TourGroupDetailDao {

    public void insert(TourGroupDetail tourGroupDetail);
    public void update(TourGroupDetail tourGroupDetail);
    public void deleteById(Integer id);
    public TourGroupDetail selectById(Integer id);
    public List<TourGroupDetail> getAll();
}
