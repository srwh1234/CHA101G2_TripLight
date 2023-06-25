package com.tw.trip.repository;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.trip.pojo.Trip;
@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
	//
    List<Trip> findByCityContaining(String cityName);

	List<Trip> findAllByOrderByTotalSalesDesc(Pageable pageable);
	
	// 找出符合編號陣列的票券
		public List<Trip> findByTripIdIn(Collection<Integer> array);
}
