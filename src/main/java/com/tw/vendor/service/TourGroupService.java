package com.tw.vendor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tw.vendor.model.TourGroup;

@Service
public interface TourGroupService {
	
	public void save(TourGroup tourGroup);

	public List<TourGroup> findAll();
    
	public void update(TourGroup tourGroup);
    
	public TourGroup findById(int tourGroupId);
    
//	public void deleteById(int tripId); 

}
