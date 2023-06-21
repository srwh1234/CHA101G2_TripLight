package com.tw.vendor.controller;

import java.util.List;

import com.tw.form.service.EmailSenderService;
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
        emailSenderService.sendEmail("triplight0411@gmail.com","廠商申請表單",vendor.toString()); // 寄信
        return "成功拿到資料";
    }

    // 更改狀態- 啟用
    @PutMapping ("/vendors1/{vendorId}")
    public String enableVendor(@PathVariable int vendorId) throws MessagingException {
        Vendor vendor = vendorService.findById(vendorId);
        vendor.setReview(1);
        vendorService.save(vendor);
        String htmlContent = """
                <!DOCTYPE html>
                        <html>
                        
                        <head>
                            <style>
                                body {
                                    font-family: Arial, sans-serif;
                                    background-color: #F5F5F5;
                                }
                        
                                .container {
                                    max-width: 800px;
                                    margin: 0 auto;
                                    padding: 30px 20px;
                                    background-color: #fafafa;
                                    border-radius: 15px;
                                    border: 1px solid #E8E8E8;
                                }
                        
                                h1 {
                                    color: #5C6BC0;
                                    text-align: center;
                                    margin-top: 0;
                                    font-size: 2.5em;
                        
                                }
                        
                                p {
                                    font-size: 1.2em;
                                    font-weight: 400;
                                    color: #333;
                                    margin-bottom: 10px;
                                    line-height: 1.6;
                                }
                        
                                .info {
                                    margin-top: 30px;
                                    padding: 10px;
                                    border: 1px solid #BDBDBD;
                                    border-radius: 10px;
                                    background-color: #ECEFF1;
                                    text-align: center;
                                }
                        
                                .button-container {
                                    text-align: center;
                                    margin-top: 30px;
                                }
                        
                                .button {
                                    display: inline-block;
                                    padding: 12px 24px;
                                    background-color: #5C6BC0;
                                    color: #ffffff !important;
                                    text-decoration: none;
                                    border-radius: 5px;
                                    font-weight: 700;
                                    font-size: 1.2em;
                                    text-align: center;
                                    transition: background-color 0.3s ease;
                                }
                        
                                .button:hover {
                                    background-color: #3949AB;
                                }
                            </style>
                        </head>
                        
                        <body>
                            <div class="container">
                                <h1>恭喜TripLight廠商帳號啟用成功</h1>
                        
                                <div class="info">
                                    <p>您的註冊email為：%s</p>
                                    <p>您的帳號為：%s</p>
                                    <p>您的密碼為：%s</p>
                                </div>
                        
                                <div class="button-container">
                                    <a href="http://localhost:8080/vendor-end/login.html" class="button">前往登入</a>
                                </div>
                            </div>
                        </body>
                        
                        </html>
                        
                """;
        String formattedMessage = String.format(htmlContent,vendor.getVendorEmail() ,vendor.getLoginAccount(), vendor.getLoginPassword());
        emailSenderService.sendHTMLEmail(vendor.getVendorEmail(),"恭喜TripLight廠商帳號啟用成功",formattedMessage);
        return "廠商啟用成功";
    }
    // 更改狀態- 停權
    @PutMapping ("/vendors2/{vendorId}")
    public String suspensionVendor(@PathVariable int vendorId){
        Vendor vendor = vendorService.findById(vendorId);
        vendor.setReview(2);
        vendorService.save(vendor);
        emailSenderService.sendEmail(vendor.getVendorEmail(),"恭喜TripLight廠商帳號啟用成功","哈哈笨蛋你被停權了");
        return "廠商停權成功";
    }



}
