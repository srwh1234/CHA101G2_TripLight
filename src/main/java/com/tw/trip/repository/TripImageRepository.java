package com.tw.trip.repository;

import com.tw.trip.model.TripImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripImageRepository extends JpaRepository<TripImage, Integer> {
}
