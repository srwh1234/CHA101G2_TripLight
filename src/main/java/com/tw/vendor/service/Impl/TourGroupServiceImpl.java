package com.tw.vendor.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tw.vendor.dao.TourGroupRepository;
import com.tw.vendor.model.TourGroup;
import com.tw.vendor.service.TourGroupService;

public class TourGroupServiceImpl implements TourGroupService {
	
	@Autowired
	private TourGroupRepository tourGroupRepository  ;

	@Override
	public void save(TourGroup tourGroup) {
		tourGroupRepository.save(tourGroup);
	}

	@Override
	public List<TourGroup> findAll() {
		return tourGroupRepository.findAll();
	}

	@Override
	public void update(TourGroup tourGroup) {
		tourGroupRepository.save(tourGroup);
	}

	@Override
	public TourGroup findById(int tourGroupId) {
		return tourGroupRepository.findById(tourGroupId).orElse(null);
	}
	
//	@Override
//	public void deleteById(int tourGroupId) {
//		tourGroupRepository.deleteById(tourGroupId);
//	}

}
