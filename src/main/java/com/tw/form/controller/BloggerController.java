package com.tw.form.controller;

import com.tw.form.dto.BloggerData;
import com.tw.form.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BloggerController {

    private EmailSenderService emailSenderService;

    @Autowired
    public BloggerController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/bloggers")
    public Boolean getBlogger(@RequestBody BloggerData bloggerData){
        emailSenderService.sendEmail("部落客與攝影師合作表單",bloggerData.toString());
        return true;
    }
}
