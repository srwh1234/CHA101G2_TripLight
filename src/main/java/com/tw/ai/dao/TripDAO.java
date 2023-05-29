package com.tw.ai.dao;



import com.tw.ai.entity.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripDAO extends JpaRepository<Trip, Integer> {
    List<Trip> findByCityContaining(String cityName);

}
