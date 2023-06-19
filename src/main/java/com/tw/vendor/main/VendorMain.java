package com.tw.vendor.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tw.vendor.dao.VendorRepository;

@Component  // 給Spring IoC 託管
public class VendorMain implements CommandLineRunner { 			// 這個class會在伺服器部屬完執行(main方法)

    @Autowired   // 注入studentRepository實例 (dao)
    private VendorRepository vendorRepository;

    @Override
    public void run(String... args){
        var vendors = vendorRepository.findAll();
//        System.out.println(vendors); // 顯示查詢到的資料
    }
}
