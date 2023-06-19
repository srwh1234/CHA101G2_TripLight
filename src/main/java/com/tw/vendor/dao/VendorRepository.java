package com.tw.vendor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.vendor.model.Vendor;


@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {
	
    //根據用戶名稱email查詢數據
    public Vendor findByVendorEmail(String email);

    public Vendor findByVendorId(int vendorId);    
}