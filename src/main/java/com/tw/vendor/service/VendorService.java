package com.tw.vendor.service;

import java.util.List;

import com.tw.vendor.model.Vendor;

public interface VendorService {

    public void save(Vendor vendor);

    public List<Vendor> findAll();   
    
	public void update(Vendor vendor);

    public Vendor findById(int vendorId);
    
//	public void deleteById(int vendorId); 
    
}
