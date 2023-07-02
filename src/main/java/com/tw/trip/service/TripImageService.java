package com.tw.trip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.trip.repository.TripImageRepository;

@Service
public class TripImageService {
	@Autowired
	private TripImageRepository tripImageRepository;

	public static String IMG_URL = "/img/trips/";

	public String findImgUrl(final int tripId) {
		final List<Integer> arrays = tripImageRepository.findIdsByTripId(tripId);

		if (arrays.isEmpty()) {
			return IMG_URL + 0;
		}
		return IMG_URL + arrays.get(0);
	}
}
