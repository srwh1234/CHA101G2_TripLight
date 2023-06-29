package com.tw.vendor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.trip.pojo.TourGroupDetail;

@Repository
public interface TourGroupDetailRepository2 extends JpaRepository<TourGroupDetail, Integer> {
	//
	public List<TourGroupDetail> findByTourGroupId(int tourGroupId);
}
