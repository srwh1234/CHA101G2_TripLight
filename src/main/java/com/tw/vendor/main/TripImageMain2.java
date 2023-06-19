package com.tw.vendor.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tw.vendor.dao.TripImageRepository2;

import jakarta.persistence.Table;

@Table(name="TripImage")
@Component  // 給Spring IoC 託管
public class TripImageMain2 implements CommandLineRunner { // 這個class會在伺服器部屬完執行(main方法)

    @Autowired   // 注入studentRepository實例 (dao)
    private TripImageRepository2 tripImageRepository2;

    @Override
    public void run(String... args){
//        var tripimages = tripImageRepository2.findAll();
//        System.out.println(tripimages); // 顯示查詢到的資料
    }
}
