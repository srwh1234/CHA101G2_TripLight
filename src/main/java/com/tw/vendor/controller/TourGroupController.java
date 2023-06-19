package com.tw.vendor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.vendor.model.TourGroup;
import com.tw.vendor.service.TourGroupService;

@CrossOrigin(origins = "*")
@RestController("tourGroupController") // 標示該類別與前端交互，自動轉Json
@RequestMapping("/tourgroup")
public class TourGroupController {

	 @Autowired // 注入TripRepository實例 (dao)
	 private TourGroupService tourGroupService;
	
	 @GetMapping("/tourgroup2") // 對應前端get, 傳資料給前端
	 public List<TourGroup> getTourGroup() {
		 return tourGroupService.findAll();
	 }
	
	 @PostMapping("/tourgroup3")
	 public String processTourGroup(@RequestBody final TourGroup tourGroup) {
		 tourGroupService.save(tourGroup);
		 System.out.println(tourGroup);
		 return "成功拿到資料";
	 }
	 
}

