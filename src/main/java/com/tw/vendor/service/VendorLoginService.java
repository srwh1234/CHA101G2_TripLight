package com.tw.vendor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tw.vendor.dao.VendorRepository;
import com.tw.vendor.model.Vendor;

@Service
public class VendorLoginService {
	
	@Autowired
	private VendorRepository vendorRepository;
	
	public boolean register(String vendorEmail, String loginAccount, String loginPassword) {
		Vendor exisitingVendor = vendorRepository.findByVendorEmail(vendorEmail);	
		if(exisitingVendor != null) {
			return false;
		}
		
		Vendor vendor = new Vendor();
		vendor.setVendorEmail(vendorEmail);
        vendor.setLoginAccount(loginAccount);
        vendor.setLoginPassword(loginPassword);
        vendorRepository.save(vendor);        
        return true; // 註冊成功
	
	}
	
	public Vendor vendorlogin(String vendorEmail, String loginAccount, String loginPassword) {
		Vendor vendor = vendorRepository.findByVendorEmail(vendorEmail);	
		if(vendor != null && loginAccount.equals(vendor.getLoginAccount())) {
			return vendor;
		}
		return null;
	}	
	
}
