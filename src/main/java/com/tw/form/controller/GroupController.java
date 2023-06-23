package com.tw.form.controller;



import com.tw.form.dto.GroupData;
import com.tw.form.service.EmailSenderService;
import com.tw.form.util.HTMLFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController {

    private final EmailSenderService emailSenderService;

    public GroupController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/groups")
    public Boolean getGroup(@RequestBody GroupData groupData){
        String htmlFormat = HTMLFormat.getHTMLFormat("TripLight團體客製表單", "blue", groupData.toString());

        emailSenderService.sendHTMLEmail("triplight0411@gmail.com","團體客製表單",htmlFormat);
        return true;
    }
}
