// package com.tw.vendor.main;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;
//
// import com.tw.vendor.dao.TripRepository;
//
// @Component // 給Spring IoC 託管
// public class TripMain implements CommandLineRunner { // 這個class會在伺服器部屬完執行(main方法)
//
// @Autowired // 注入tripRepository實例 (dao)
// private TripRepository tripRepository;
//
// @Override
// public void run(String... args){
// var trips = tripRepository.findAll();
// System.out.println(trips); // 顯示查詢到的資料
// }
//
// }
