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
        return  "姓名："+ name +"\n"+
                "電話："+ phone +"\n"+
                "信箱："+ email +"\n"+
                "其他需求："+ message +"\n";
    }
}
