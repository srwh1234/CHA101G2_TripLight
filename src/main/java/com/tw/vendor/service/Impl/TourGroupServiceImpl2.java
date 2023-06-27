package com.tw.vendor.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tw.vendor.dao.TourGroupRepository2;
import com.tw.vendor.model.TourGroup2;
import com.tw.vendor.service.TourGroupService2;
import org.springframework.stereotype.Service;


@Service
public class TourGroupServiceImpl2 implements TourGroupService2 {
	
	@Autowired
	private TourGroupRepository2 tourGroupRepository  ;

	@Override
	public void save(TourGroup2 tourGroup) {
		tourGroupRepository.save(tourGroup);
	}
	
	@Override
	public List<TourGroup2> findAll() {
		return tourGroupRepository.findAll();
	}

	@Override
	public void update(TourGroup2 tourGroup) {
		tourGroupRepository.save(tourGroup);
	}

	@Override
	public TourGroup2 findById(int tourGroupId) {
		return tourGroupRepository.findById(tourGroupId).orElse(null);
	}
	
//	@Override
//	public void deleteById(int tourGroupId) {
//		tourGroupRepository.deleteById(tourGroupId);
//	}

}
