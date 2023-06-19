package com.tw.vendor.model;

 import java.sql.Timestamp;

 import jakarta.persistence.Entity;
 import jakarta.persistence.GeneratedValue;
 import jakarta.persistence.GenerationType;
 import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

 @Entity
 @Data
 @Table(name="TripImage")
 public class TripImage2 {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int id;
	
	 private int tripId;
	
	 private byte[] image;
	
	 private Timestamp uploadTime;
	 
 }