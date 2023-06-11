package com.tw.trip.model.dao;

import com.tw.trip.model.pojo.TripComment;

import java.util.List;
import java.util.Map;

public interface TripCommentDao {
    public void insert(TripComment tripComment);
    public void update(TripComment tripComment);
    public void delete(TripComment tripComment);
    public TripComment findByPrimaryKey(Integer pk);
    public List<TripComment> getAll();
    public List<TripComment> getTripComments();

}
