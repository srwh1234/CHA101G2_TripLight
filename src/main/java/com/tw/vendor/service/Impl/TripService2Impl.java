package com.tw.vendor.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.vendor.dao.TripRepository2;
import com.tw.vendor.model.Trip2;
import com.tw.vendor.service.TripService2;

@Service
public class TripService2Impl implements TripService2 {

		@Autowired
		private TripRepository2 tripRepository  ;


		@Override
		public void save(Trip2 trip) {
			tripRepository.save(trip);
		}

		@Override
		public List<Trip2> findAll() {
			return tripRepository.findAll();
		}

		@Override
		public void update(Trip2 trip) {
			tripRepository.save(trip);
		}

		@Override
		public Trip2 findById(int tripId) {
			return tripRepository.findById(tripId).orElse(null);
		}

//		@Override
//		public void deleteById(int tripId) {
//			tripRepository.deleteById(tripId);
//		}

}
