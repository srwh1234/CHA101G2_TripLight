package com.tw.trip.dao;

import com.tw.trip.pojo.TripComment;

import java.util.List;

public interface TripCommentDao {
    public void insert(TripComment tripComment);
    public void update(TripComment tripComment);
    public void deleteById(Integer id);
    public TripComment findByPrimaryKey(Integer id);
    public List<TripComment> getAll();

}
