package com.tw.vendor.controller;

import java.util.List;

import com.tw.form.service.EmailSenderService;
import com.tw.form.util.HTMLFormat;
import jakarta.mail.MessagingException;
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
        String htmlFormat = HTMLFormat.getHTMLFormat("TripLight廠商申請表單", "blue", vendor.toString(),"前往後臺審核!", "http://localhost:8080/back-end/index.html");
        emailSenderService.sendHTMLEmail("triplight0411@gmail.com","廠商申請表單",htmlFormat); // 寄信
        return "成功拿到資料";
    }

    // 更改狀態- 啟用
    @PutMapping ("/vendors1/{vendorId}")
    public String enableVendor(@PathVariable int vendorId) throws MessagingException {
        Vendor vendor = vendorService.findById(vendorId);
        vendor.setReview(1);
        vendorService.save(vendor);
        String message = "您的註冊email為：%s<br>您的帳號為：%s<br>您的密碼為：%s";
        String content = String.format(message,vendor.getVendorEmail() ,vendor.getLoginAccount(), vendor.getLoginPassword());
        String htmlFormat = HTMLFormat.getHTMLFormat("恭喜TripLight廠商帳號啟用成功", "green", content, "前往登入!", "http://localhost:8080/vendor-end/login.html");
        emailSenderService.sendHTMLEmail(vendor.getVendorEmail(),"恭喜TripLight廠商帳號啟用成功",htmlFormat);
        return "廠商啟用成功";
    }
    // 更改狀態- 停權
    @PutMapping ("/vendors2/{vendorId}")
    public String suspensionVendor(@PathVariable int vendorId) throws MessagingException {
        Vendor vendor = vendorService.findById(vendorId);
        vendor.setReview(2);
        vendorService.save(vendor);
        String content = "您的廠商帳號已被停權，如有問題請聯繫客服<br>客服電話：0987987987";
        String htmlFormat = HTMLFormat.getHTMLFormat("TripLight廠商帳號停權通知", "red", content, "聯繫客服", "http://localhost:8080/front-end/index.html");
        emailSenderService.sendHTMLEmail(vendor.getVendorEmail(),"TripLight廠商帳號停權通知",htmlFormat);
        return "廠商停權成功";
    }







}
