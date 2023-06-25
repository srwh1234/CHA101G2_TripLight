package com.tw.trip.repository;

import com.tw.trip.pojo.TripImage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripImageRepository extends JpaRepository<TripImage, Integer> {
	// 找出指定票券編號的圖片編號清單
	@Query("SELECT t.id FROM TripImage t WHERE t.tripId=:id")
	public List<Integer> findIdsByTripId(@Param("id") int tripId);
}
