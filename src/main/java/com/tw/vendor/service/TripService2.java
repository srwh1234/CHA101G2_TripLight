package com.tw.vendor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.vendor.dao.TripRepository2;
import com.tw.vendor.model.Trip2;
import jakarta.persistence.Table;

@Table(name="Trip")
@Service
public class TripService2 {
	 private TripRepository2 tripRepository2;

	 @Autowired
	 public TripService2(TripRepository2 tripRepository2) {
		 this.tripRepository2 = tripRepository2;
	 }

	 public void save(Trip2 trip){
				// 可以加入其他業務流程
		 tripRepository2.save(trip);
	 }

	 public List<Trip2> findAll(){
				// 可以加入其他業務流程
		 return tripRepository2.findAll();
	 }
    
}


