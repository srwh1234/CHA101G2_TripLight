package com.tw.trip.model.dao;

import com.tw.trip.model.pojo.TourGroup;

import java.util.List;

public interface TourGroupDao {
    public void insert(TourGroup tourGroup);
    public void update(TourGroup tourGroup);
    public void deleteById(Integer id);
    public TourGroup selectById(Integer id);

    public List<TourGroup> getAll();
}
