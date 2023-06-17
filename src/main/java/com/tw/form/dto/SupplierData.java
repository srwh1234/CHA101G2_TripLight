package com.tw.form.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierData {
    String companyName;
    String contactName;
    String contactEmail;
    String contactPhone;
    String serviceType;
    String message;
    String registrationLink;

    @Override
    public String toString() {
        return  "公司名稱："+ companyName +"\n"+
                "聯絡人姓名："+ contactName +"\n"+
                "聯絡人電子郵件："+ contactEmail +"\n"+
                "聯絡人電話："+ contactPhone +"\n"+
                "服務類型："+ serviceType +"\n"+
                "訊息："+ message +"\n";
    }
}
