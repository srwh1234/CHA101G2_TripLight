package com.tw.vendor.model;

import java.sql.Timestamp;

import com.tw.vendor.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TripImage {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private int tripId;

	    private byte[] image;

	    private Timestamp uploadTime;
}