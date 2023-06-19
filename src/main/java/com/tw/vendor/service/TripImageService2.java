package com.tw.vendor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tw.vendor.model.TripImage2;

@Service
public interface TripImageService2 {
    
    public byte[] findImg(final int id);
	 
    public void save(TripImage2 tripImage2);

    public List<TripImage2> findAll();
	    
}
