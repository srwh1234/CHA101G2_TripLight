package com.tw.form.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BloggerData {
    String name;
    String phone;
    String email;
    String portfolioLink;
    String message;

    @Override
    public String toString() {
        return  "姓名："+ name +"<br>"+
                "電話："+ phone +"<br>"+
                "信箱："+ email +"<br>"+
                "作品連結："+ portfolioLink +"<br>"+
                "其他需求："+ message +"<br>";
    }
}
