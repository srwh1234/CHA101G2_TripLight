package com.tw.form.controller;


import com.tw.form.dto.contactData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @PostMapping("/contacts")
    public Boolean getContact(@RequestBody contactData contactData){
        System.out.println(contactData);
        return true;
    }
}
