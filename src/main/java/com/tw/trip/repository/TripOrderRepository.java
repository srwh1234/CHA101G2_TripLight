package com.tw.trip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.trip.model.pojo.TripOrder;
@Repository
public interface TripOrderRepository extends JpaRepository<TripOrder, Integer> {
	// 找出同一個Member的TripOrder
	public List<TripOrder> findByMemberId(int memberId);



}
