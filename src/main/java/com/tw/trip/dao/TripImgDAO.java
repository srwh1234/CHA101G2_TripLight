package com.tw.trip.dao;




import com.tw.trip.entity.TripImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripImgDAO extends JpaRepository<TripImage, Integer> {

    List<TripImage> findByTripId(int tripId);
}
