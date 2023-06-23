package com.tw.form.controller;


import com.tw.form.dto.ContactData;
import com.tw.form.service.EmailSenderService;
import com.tw.form.util.HTMLFormat;
import jakarta.mail.MessagingException;
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
        String htmlFormat = HTMLFormat.getHTMLFormat("TripLight聯絡我們表單", "blue", contactData.toString());
        emailSenderService.sendHTMLEmail("triplight0411@gmail.com","聯絡我們表單",htmlFormat);
        return true;
    }
}
