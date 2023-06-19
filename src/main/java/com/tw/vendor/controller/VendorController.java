package com.tw.vendor.controller;

import java.util.List;

import com.tw.form.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.tw.vendor.dao.VendorRepository;
import com.tw.vendor.model.Vendor;
import com.tw.vendor.service.VendorService;

@CrossOrigin(origins = "*")
@RestController  // 標示該類別與前端交互，自動轉Json
@RequestMapping("/vendor")
public class VendorController {
    
    @Autowired  
    private VendorService vendorService;

<<<<<<< HEAD
    @GetMapping("/vendorlist")  // 對應前端get,  傳資料給前端
=======
    @Autowired
    private EmailSenderService emailSenderService; // 寄信用

    @GetMapping("/vendor")  // 對應前端get,  傳資料給前端
>>>>>>> 8f8bd6ee5c62606cdb24c7349c98e7b49c85c095
    public List<Vendor> getveVendors() {
        return vendorService.findAll();
    }
    
    @PostMapping("/vendoradd")
    public String processVendor(@RequestBody Vendor vendor){
        vendorService.save(vendor);
<<<<<<< HEAD
=======
        emailSenderService.sendEmail("廠商申請表單",vendor.toString()); // 寄信
>>>>>>> 8f8bd6ee5c62606cdb24c7349c98e7b49c85c095
        return "成功拿到資料";
    }
    
}
