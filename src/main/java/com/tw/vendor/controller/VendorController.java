package com.tw.vendor.controller;

import java.util.List;

import com.tw.form.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tw.vendor.model.Vendor;
import com.tw.vendor.service.VendorService;

@RestController
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private EmailSenderService emailSenderService; // 寄信用

    @GetMapping("/vendors")  // 對應前端get,  傳資料給前端
    public List<Vendor> getveVendors() {
        return vendorService.findAll();
    }

    @PostMapping("/vendors")
    public String processVendor(@RequestBody Vendor vendor){
    	System.out.println("Get");
        vendorService.save(vendor);
        emailSenderService.sendEmail("廠商申請表單",vendor.toString()); // 寄信
        return "成功拿到資料";
    }

    // 更改狀態- 啟用
    @PutMapping ("/vendors1/{vendorId}")
    public String enableVendor(@PathVariable int vendorId){
        Vendor vendor = vendorService.findById(vendorId);
        vendor.setReview(1);
        vendorService.save(vendor);
        return "廠商啟用成功";
    }
    // 更改狀態- 停權
    @PutMapping ("/vendors2/{vendorId}")
    public String suspensionVendor(@PathVariable int vendorId){
        Vendor vendor = vendorService.findById(vendorId);
        vendor.setReview(2);
        vendorService.save(vendor);
        return "廠商停權成功";
    }



}
