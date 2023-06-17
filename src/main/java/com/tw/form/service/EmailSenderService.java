package com.tw.form.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    // 使用 JavaMailSender 來執行
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    // 傳輸mail

    public boolean sendEmail(String subject, String message) {
        var mailMessage = new SimpleMailMessage();
        mailMessage.setTo("triplight0411@gmail.com");
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
        return true;
    }
}
