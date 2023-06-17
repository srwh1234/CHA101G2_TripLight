package com.tw.form.controller;


import com.tw.form.dto.ContactData;
import com.tw.form.dto.GroupData;
import com.tw.form.service.EmailSenderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController {

    private EmailSenderService emailSenderService;

    public GroupController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/groups")
    public Boolean getGroup(@RequestBody GroupData groupData){
        System.out.println(groupData);
        emailSenderService.sendEmail("團體客製表單",groupData.toString());
        return true;
    }
}
