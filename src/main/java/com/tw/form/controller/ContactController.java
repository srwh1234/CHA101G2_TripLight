package com.tw.form.controller;


import com.tw.form.dto.ContactData;
import com.tw.form.service.EmailSenderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    private final EmailSenderService emailSenderService;

    public ContactController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/contacts")
    public Boolean getContact(@RequestBody ContactData contactData){
        emailSenderService.sendEmail("聯絡我們表單",contactData.toString());
        return true;
    }
}
