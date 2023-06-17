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
        return  "姓名："+ name +"\n"+
                "電話："+ phone +"\n"+
                "信箱："+ email +"\n"+
                "團體人數："+ groupSize +"\n"+
                "行程天數："+ tripDuration +"\n"+
                "首選目的地："+ preferredDestination +"\n"+
                "行程偏好："+ preferredActivity +"\n"+
                "其他需求："+ message +"\n";
    }
}
