package com.tw.trip.repository;
import com.tw.trip.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findByCityContaining(String cityName);

}
