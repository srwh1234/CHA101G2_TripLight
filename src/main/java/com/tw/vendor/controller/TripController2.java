package com.tw.vendor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.vendor.dao.TripRepository2;
import com.tw.vendor.model.Trip2;
import com.tw.vendor.model.Vendor;
import com.tw.vendor.service.TripService2;

import jakarta.servlet.http.HttpSession;

 @CrossOrigin(origins = "*")
 @RestController // 標示該類別與前端交互，自動轉Json
 @RequestMapping("/trip")
 public class TripController2 {

	 @Autowired
	 private TripService2 tripService2;
	 
	 @Autowired
	 private TripRepository2 tripRepository2;

	 @GetMapping("/trip2") // 對應前端get, 傳資料給前端
	 public List<Trip2> getTrips() {
		 return tripService2.findAll();
	 }
	 
	 @GetMapping("/info")
	    public List<Trip2> getTripInfo(HttpSession httpSession) {
		 //System.out.println( " " + httpSession.getId());
		 Vendor vendor = (Vendor) httpSession.getAttribute("vendor");
		 return tripRepository2.findByVendorId(vendor.getVendorId());
	    }

	 @PostMapping("/tripadd")
	 public String processTrip(@RequestBody final Trip2 trip) {
		 tripService2.save(trip);
		 //System.out.println(trip);
		 return "成功拿到資料";
	 }
	 
	 @PostMapping("/edittrip")
	 public String editTrip(@RequestBody final Trip2 trip) {
		 tripService2.save(trip);
		 //System.out.println(trip);
		 return "成功拿到資料";
	 }
 }
