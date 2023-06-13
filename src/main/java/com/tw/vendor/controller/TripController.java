// package com.tw.vendor.controller;
//
// import java.util.List;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;
//
// import com.tw.vendor.model.Trip;
// import com.tw.vendor.service.TripService;
//
// @RestController // 標示該類別與前端交互，自動轉Json
// public class TripController {
//
// @Autowired // 注入TripRepository實例 (dao)
// private TripService tripService;
//
// @GetMapping("/trip") // 對應前端get, 傳資料給前端
// public List<Trip> getTrips() {
// return tripService.findAll();
// }
//
// @PostMapping("/trip")
// public String processStudent(@RequestBody final Trip trip) {
// tripService.save(trip);
// System.out.println(trip);
// return "成功拿到資料";
// }
// }
