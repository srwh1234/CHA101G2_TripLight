package com.tw.vendor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.trip.pojo.TripOrder;

@Repository
public interface TripOrderRepository2 extends JpaRepository<TripOrder, Integer> {

	public List<TripOrder> findByTourGroupId(int tourGroupId);
}
