package com.tw.vendor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tw.vendor.model.TourGroup2;

@Service
public interface TourGroupService2 {
	
	public void save(TourGroup2 tourGroup);

	public List<TourGroup2> findAll();
    
	public void update(TourGroup2 tourGroup);
    
	public TourGroup2 findById(int tourGroupId);
    
//	public void deleteById(int tripId); 

}
