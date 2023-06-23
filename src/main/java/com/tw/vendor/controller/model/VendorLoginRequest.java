package com.tw.vendor.controller.model;

import lombok.Data;

@Data
public class VendorLoginRequest {
	private String vendorEmail;
	private String loginAccount;
	private String loginPassword;

}
