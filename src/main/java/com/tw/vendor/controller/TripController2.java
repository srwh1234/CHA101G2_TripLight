package com.tw.vendor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.vendor.model.Trip2;
import com.tw.vendor.service.TripService2;

 @CrossOrigin(origins = "*")
 @RestController("tripController") // 標示該類別與前端交互，自動轉Json
 @RequestMapping("/trip")
 public class TripController2 {

	 @Autowired
	 private TripService2 tripService2;

	 @GetMapping("/trip2") // 對應前端get, 傳資料給前端
	 public List<Trip2> getTrips() {
		 return tripService2.findAll();
	 }

	 @PostMapping("/trip3")
	 public String processStudent(@RequestBody final Trip2 trip) {
		 tripService2.save(trip);
		 System.out.println(trip);
		 return "成功拿到資料";
	 }
 }
