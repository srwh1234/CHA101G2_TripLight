package com.tw.vendor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.vendor.dao.TourGroupDetailRepository2;
import com.tw.vendor.dao.TourGroupRepository2;
import com.tw.vendor.dao.TripRepository2;
import com.tw.vendor.model.TourGroup2;
import com.tw.vendor.model.Trip2;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vd")
public class VendorTourOrderController {

	@Autowired
	private TourGroupRepository2 tourGroupRepository;

	@Autowired
	private TourGroupDetailRepository2 tourGroupDetailRepository;

	@Autowired
	private TripRepository2 tripRepository;

	@GetMapping("/tourOrders")
	public List<TourOrderDto> tourOrders() {
		final List<TourOrderDto> result = new ArrayList<>();
		for (final TourGroup2 tg : tourGroupRepository.findAll()) {

			final Trip2 trip = tripRepository.findById(tg.getTripId()).orElse(null);

			if (trip == null) {
				continue;
			}

			final TourOrderDto dto = new TourOrderDto();
			dto.setTourGroupId(tg.getTourGroupId());
			dto.setTripName(trip.getTripName());
			dto.setConfirmedTravelersNo(tg.getConfirmedTravelersNo());
			dto.setMinTravelersNo(tg.getMinTravelersNo());
			dto.setCurTravelersNo(tourGroupDetailRepository.findByTourGroupId(tg.getTourGroupId()).size());
			result.add(dto);
		}

		return result;
	}

	@Data
	public static class TourOrderDto {
		private int tourGroupId;
		private String tripName;
		private int confirmedTravelersNo;
		private int minTravelersNo;
		private int curTravelersNo;
	}
}
