package com.tw.vendor.controller.model;

import lombok.Data;

@Data
public class VendorLoginResponse {
	
	private String vendorName;
	
	private int vendorId;
	
	private boolean success; 
}
