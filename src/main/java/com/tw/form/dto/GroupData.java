package com.tw.form.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupData {
    String name;
    String email;
    String phone;
    Integer groupSize;
    Integer tripDuration;
    String preferredDestination;
    String preferredActivity;
    String message;

    @Override
    public String toString() {
        return  "姓名："+ name +"<br>"+
                "電話："+ phone +"<br>"+
                "信箱："+ email +"<br>"+
                "團體人數："+ groupSize +"<br>"+
                "行程天數："+ tripDuration +"<br>"+
                "首選目的地："+ preferredDestination +"<br>"+
                "行程偏好："+ preferredActivity +"<br>"+
                "其他需求："+ message +"<br>";
    }
}
