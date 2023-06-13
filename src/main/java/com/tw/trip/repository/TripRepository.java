package com.tw.trip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tw.trip.model.pojo.Trip;

public interface TripRepository extends JpaRepository<Trip, Integer> {
	//
	List<Trip> findByCityContaining(String cityName);

}
