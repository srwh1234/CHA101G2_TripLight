package com.tw.trip.dao;
import com.tw.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findByCityContaining(String cityName);

}
