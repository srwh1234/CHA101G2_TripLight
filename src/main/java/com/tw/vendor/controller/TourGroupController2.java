package com.tw.vendor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.vendor.model.TourGroup2;
import com.tw.vendor.service.TourGroupService2;

@CrossOrigin(origins = "*")
@RestController("tourGroupController") // 標示該類別與前端交互，自動轉Json
@RequestMapping("/tourgroup")
public class TourGroupController2 {

	 @Autowired
	 private TourGroupService2 tourGroupService;
	
	 @GetMapping("/tourgroup2") // 對應前端get, 傳資料給前端
	 public List<TourGroup2> getTourGroup() {
		 return tourGroupService.findAll();
	 }
	
	 @PostMapping("/tourgroup3")
	 public String processTourGroup(@RequestBody final TourGroup2 tourGroup) {
		 tourGroupService.save(tourGroup);
		 System.out.println(tourGroup);
		 return "成功拿到資料";
	 }
	 
}

