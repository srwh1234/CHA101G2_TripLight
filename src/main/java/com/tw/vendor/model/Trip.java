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
public class Trip {  // 表格名稱
    @Id  // 標示id為主鍵
    private int tripId; 
    
    private int vendorId;
    
    private int tripTypeId;
    
    private String tripName;
    
    private String tripDescription;
    
    private String tripNote;
    
    private int tripDay;
    
    private String city;
    
    private int totalSales;
    
    private int priceAdult;
    
    private int priceChild;
    
    private int minTravelersNo;
    
    private int maxRavelersNo;
    
    private double ratinSum;
    
    private int ratingCount;
    
    private byte status;
    
}
