package com.tw.vendor.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.vendor.dao.VendorRepository;
import com.tw.vendor.model.Vendor;
import com.tw.vendor.service.VendorService;

@Service
public class VendorServiceImpl implements VendorService {
	
	@Autowired
	private VendorRepository vendorRepository;

	@Override
	public void save(Vendor vendor) {
		vendorRepository.save(vendor);
	}

	@Override
	public List<Vendor> findAll() {
		return vendorRepository.findAll();
	}

	@Override
	public void update(Vendor vendor) {
		vendorRepository.save(vendor);
	}

	@Override
	public Vendor findById(int vendorId) {
		return vendorRepository.findById(vendorId).orElse(null);
	}

//	@Override
//	public void deleteById(int vendorId) {
//		vendorRepository.deleteById(vendorId);
//	}

}
