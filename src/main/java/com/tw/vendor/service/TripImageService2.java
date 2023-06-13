package com.tw.vendor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.vendor.dao.TripImageRepository2;
import com.tw.vendor.model.TripImage2;

import jakarta.persistence.Table;

@Table(name="TripImage")
@Service
public class TripImageService2 {
	private TripImageRepository2 tripImageRepository2;

	 @Autowired
	 public void TripService2(TripImageRepository2 tripImageRepository2) {
	     this.tripImageRepository2 = tripImageRepository2;
	 }
	 
	 public byte[] findImg(final int id) {
	        var tripImage2 = tripImageRepository2.findById(id);
	        // TripImage::getImage：引用TripImage的getImage()
	        // map (裡面對象如果存在則執行)
	        return tripImage2.map(TripImage2::getImage).orElse(null);
	    }

	    public void save(TripImage2 tripImage2){
					// 可以加入其他業務流程
	    	tripImageRepository2.save(tripImage2);
	    }

	    public List<TripImage2> findAll(){
					// 可以加入其他業務流程
	        return tripImageRepository2.findAll();
	    }
	    
}
