package com.tw.vendor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.DynamicInsert;
import java.sql.Timestamp;

// 標示該類別為實體類別，用於映射資料庫
@Entity
@Getter
@Setter
@ToString
@Table
@DynamicInsert  //這個注解可以讓 Hibernate 在插入新記錄時只生成非空欄位的 SQL 語句，從而避免將 NULL 值插入 application_time 欄位。
public class Vendor {  // 表格名稱
	
    @Id  // 標示id為主鍵
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        return  "公司名稱："+ vendorName +"<br>"+
                "公司地址："+ vendorAdd +"<br>"+
                "公司電話："+ vendorPhone +"<br>"+
                "聯絡人姓名："+ ceoName +"<br>"+
                "聯絡人電子郵件："+ ceoEmail +"<br>"+
                "聯絡人電話："+ ceoPhone +"<br>"+
                "公司登入帳號："+ loginAccount +"<br>"+
                "公司登入密碼："+ vendorEmail +"<br>"+
                "公司信箱："+ vendorEmail +"<br>";
    }

	public Vendor orElse(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
