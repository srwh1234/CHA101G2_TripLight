<<<<<<< HEAD
package com.tw.vendor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.trip.model.TripImage;
import com.tw.trip.repository.TripImageRepository;
import com.tw.vendor.dao.TripRepository;
import com.tw.vendor.model.Trip;

@Service
public class TripService {
	 private TripRepository tripRepository;
	 private final TripImageRepository tripImageRepository;

	 @Autowired
	 public TripService(TripRepository tripRepository, TripImageRepository tripImageRepository) {
		 this.tripRepository = tripRepository;
	     this.tripImageRepository = tripImageRepository;
	 }
     
	 public byte[] findImg(final int id) {
	        var tripImage = tripImageRepository.findById(id);
	        // TripImage::getImage：引用TripImage的getImage()
	        // map (裡面對象如果存在則執行)
	        return tripImage.map(TripImage::getImage).orElse(null);
	    }

	 public void save(Trip trip){
				// 可以加入其他業務流程
		 tripRepository.save(trip);
	 }

	 public List<Trip> findAll(){
				// 可以加入其他業務流程
		 return tripRepository.findAll();
	 }
    
}
=======
// package com.tw.vendor.service;
//
// import java.util.List;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
//
// import com.tw.vendor.dao.TripRepository;
// import com.tw.vendor.model.Trip;
//
// @Service
// public class TripService {
//
// @Autowired
// private TripRepository tripRepository;
//
// public void save(Trip trip){
// // 可以加入其他業務流程
// tripRepository.save(trip);
// }
//
// public List<Trip> findAll(){
// // 可以加入其他業務流程
// return tripRepository.findAll();
// }
//
// }
>>>>>>> 084773ff63fdf70b73d3c68fac02f064ac9951f2
