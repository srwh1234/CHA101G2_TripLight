package com.tw.vendor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tw.vendor.model.VendorImage;

@Service
public interface VendorImageService {
	
	public byte[] findImg(final int id);

	 public void save(VendorImage vendorImage);

	 public List<VendorImage> findAll();

}
