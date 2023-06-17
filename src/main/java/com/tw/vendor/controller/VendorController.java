package com.tw.vendor.controller;

import java.util.List;

import com.tw.form.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import com.tw.vendor.dao.VendorRepository;
import com.tw.vendor.model.Vendor;
import com.tw.vendor.service.VendorService;

@RestController  // 標示該類別與前端交互，自動轉Json
public class VendorController {
    
    @Autowired  // 注入studentRepository實例 (dao)
    private VendorService vendorService;

    @Autowired
    private EmailSenderService emailSenderService; // 寄信用

    @GetMapping("/vendor")  // 對應前端get,  傳資料給前端
    public List<Vendor> getveVendors() {
        return vendorService.findAll();
    }
    
    @PostMapping("/vendor")
    public String processStudent(@RequestBody Vendor vendor){
        vendorService.save(vendor);
        emailSenderService.sendEmail("廠商申請表單",vendor.toString()); // 寄信
        return "成功拿到資料";
    }
    
}
