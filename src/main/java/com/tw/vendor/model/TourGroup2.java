package com.tw.vendor.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//標示該類別為實體類別，用於映射資料庫
@Entity  
@Getter
@Setter
@ToString
@Table(name = "TourGroup")
public class TourGroup2 {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tourGroupId;
	
	private int tripId;
	
	private int priceAdult;
	
	private int priceChild;
	
	private Date startDate;
	
	private int confirmedTravelersNo;
	
	private int minTravelersNo;
	
	private int maxTravelersNo;
	
	private Integer status;
	
}
