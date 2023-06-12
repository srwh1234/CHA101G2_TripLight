package com.tw.vendor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity  // 標示該類別為實體類別，用於映射資料庫
@Getter
@Setter
@ToString               
public class Vendor {  // 表格名稱
	
    @Id  // 標示id為主鍵
    private int vendorId; 
    
    private String vendorName;
    
    private String vendorAdd;
    
    private String vendorPhone;
    
    private String ceoEmail;
    
    private String ceoName;
    
    private String ceoPhone;
    
    private String applicationTime;
    
    private String review;
    
    private String bankAccount;
    
    private String contractTerm;
    
    private String loginAccount;
    
    private String loginPassword;
    
    private String vendorEmail; 
    
}
