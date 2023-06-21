package com.tw.vendor.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.vendor.dao.TripImageRepository2;
import com.tw.vendor.dao.TripRepository2;
import com.tw.vendor.model.Trip2;
import com.tw.vendor.model.TripImage2;
import com.tw.vendor.service.TripService2;

@Service
public class TripService2Impl implements TripService2 {

		@Autowired
		private TripRepository2 tripRepository  ;
		private TripImageRepository2 tripImageRepository2;


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

		@Override
		public byte[] findImg(int id) {
			var tripImage = tripImageRepository2.findById(id);
	        // TripImage::getImage：引用TripImage的getImage()
	        // map (裡面對象如果存在則執行)
	        return tripImage.map(TripImage2::getImage).orElse(null);		}

//		@Override
//		public void deleteById(int tripId) {
//			tripRepository.deleteById(tripId);
//		}

}
