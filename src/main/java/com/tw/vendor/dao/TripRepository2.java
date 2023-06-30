package com.tw.vendor.dao;

import java.util.List;

import com.tw.trip.pojo.Trip;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.vendor.model.Trip2;

@Repository
public interface TripRepository2 extends JpaRepository<Trip2, Integer> {
	
	public List<Trip2> findByVendorId(int vendorId);
	List<Trip2> findByCityContaining(String cityName);

	List<Trip2> findAllByOrderByTotalSalesDesc(Pageable pageable);
}
