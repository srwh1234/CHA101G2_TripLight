package com.tw.form.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactData {
    String name;
    String email;
    String phone;
    String message;

    @Override
    public String toString() {
        return  "姓名："+ name +"<br>"+
                "電話："+ phone +"<br>"+
                "信箱："+ email +"<br>"+
                "其他需求："+ message +"<br>";
    }
}
