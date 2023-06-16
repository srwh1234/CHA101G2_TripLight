package com.tw.form.controller;

import com.tw.form.dto.BloggerData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BloggerController {
    @PostMapping("/bloggers")
    public Boolean getBlogger(@RequestBody BloggerData bloggerData){
        System.out.println(bloggerData);
        return true;
    }
}
