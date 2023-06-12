package com.tw.vendor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.vendor.dao.VendorRepository;
import com.tw.vendor.model.Vendor;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public void save(Vendor vendor){
				// 可以加入其他業務流程
        vendorRepository.save(vendor);
    }

    public List<Vendor> findAll(){
				// 可以加入其他業務流程
        return vendorRepository.findAll();
    }
    
}
