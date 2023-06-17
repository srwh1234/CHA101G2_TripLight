package com.tw.vendor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import java.sql.Timestamp;

@Entity  // 標示該類別為實體類別，用於映射資料庫
@Getter
@Setter
@DynamicInsert  //這個注解可以讓 Hibernate 在插入新記錄時只生成非空欄位的 SQL 語句，從而避免將 NULL 值插入 application_time 欄位。
public class Vendor {  // 表格名稱
	
    @Id  // 標示id為主鍵
    private int vendorId; 
    
    private String vendorName;
    
    private String vendorAdd;
    
    private String vendorPhone;
    
    private String ceoEmail;
    
    private String ceoName;
    
    private String ceoPhone;

    private Timestamp applicationTime;
    
    private int review;
    
    private String bankAccount;
    
    private String contractTerm;
    
    private String loginAccount;
    
    private String loginPassword;
    
    private String vendorEmail;

    @Override
    public String toString() {
        return  "公司名稱："+ vendorName +"\n"+
                "公司地址："+ vendorAdd +"\n"+
                "公司電話："+ vendorPhone +"\n"+
                "聯絡人姓名："+ ceoName +"\n"+
                "聯絡人電子郵件："+ ceoEmail +"\n"+
                "聯絡人電話："+ ceoPhone +"\n"+
                "公司登入帳號："+ loginAccount +"\n"+
                "公司登入密碼："+ vendorEmail +"\n"+
                "公司信箱："+ vendorEmail +"\n";
    }
    
}
