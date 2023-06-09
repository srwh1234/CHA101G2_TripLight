package com.tw.trip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tw.trip.service.TripService;

@RestController
public class TripImageController {

	public static String IMG_URL = "/img/trips/";

	private final TripService tripService;

	@Autowired
	public TripImageController(final TripService tripService) {
		this.tripService = tripService;
	}

	// 用於指定 {imgUrl} 只能由數字組成。 [0-9] 表示匹配一個數字字符，+ 表示匹配前面的表達式一次或多次。
	@GetMapping(value = "/img/trips/{imgUrl:[0-9]+}", produces = MediaType.IMAGE_GIF_VALUE)
	public byte[] getPhoto(@PathVariable("imgUrl") final int id) {
		return tripService.findImg(id);
	}

}
