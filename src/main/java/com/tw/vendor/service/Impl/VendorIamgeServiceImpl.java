package com.tw.vendor.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tw.vendor.dao.VendorImageRepository;
import com.tw.vendor.model.VendorImage;
import com.tw.vendor.service.VendorImageService;

public class VendorIamgeServiceImpl implements VendorImageService{
	
	@Autowired
	private VendorImageRepository vendorImageRepository;
	
	@Override
	public byte[] findImg(final int id) {
	    var vendorImage = vendorImageRepository.findById(id);
	    // TripImage::getImage：引用TripImage的getImage()
	    // map (裡面對象如果存在則執行)
	    return vendorImage.map(VendorImage::getImage).orElse(null);
	}
	
	@Override
	public  void save(VendorImage vendorImage){
		// 可以加入其他業務流程
		vendorImageRepository.save(vendorImage);
	}
	
	@Override
	public List<VendorImage> findAll(){
		// 可以加入其他業務流程
	    return vendorImageRepository.findAll();
	}
}
