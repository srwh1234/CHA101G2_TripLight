package com.tw.form.controller;

import com.tw.form.dto.SupplierData;
import com.tw.form.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupplierController {
    private final EmailSenderService emailSenderService;

    @Autowired
    public SupplierController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/suppliers")
    public Boolean getSupplier(@RequestBody SupplierData supplierData){
        emailSenderService.sendEmail("triplight0411@gmail.com","廠商申請表單",supplierData.toString());
        return true;
    }
}
