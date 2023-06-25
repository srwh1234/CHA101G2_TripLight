package com.tw.trip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.trip.pojo.Trip;
import com.tw.trip.repository.TripRepository;
import com.tw.trip.service.TripFavoriteService;

import lombok.Data;
@CrossOrigin(origins = "*")
@RestController
public class TripFavoriteController {
	@Autowired
	private TripFavoriteService tripFavoriteService;
	@Autowired
	private TripRepository tripRepository;

	/**
	 * 前台-旅遊團明細-我的最愛狀態切換
	 */
	@PostMapping("/tripfavorite")
	public int tripfavorite(@RequestBody final FavoriteReqDto reqDto) {
		return tripFavoriteService.updateItem(reqDto);
	}

//確認是存在DB裡
//	@GetMapping("/existFavorite/{memberId}")
//	public boolean existFavorite(@PathVariable Integer memberId, Trip trip) {
//
//		boolean favoriteStatus = tripFavoriteService.checkIfExists(memberId, trip);
//		if (favoriteStatus) {
//			System.out.println("存在");
//			return true;
//		} else {
//			System.out.println("不存在");
//			return false;
//		}
//	}

	@Data
	public static class DetailDto {
		public DetailDto(final Trip trip) {
			this.tripId = trip.getTripId();
			this.tripName = trip.getTripName();
			this.tripDescription = trip.getTripDescription();
			this.priceAdult = trip.getPriceAdult();
			this.imageBase64 = trip.getImageBase64();
		}

		private int tripId;
		private String tripName;
		private String tripDescription;
		private int  priceAdult;
		private String imageBase64;
		private boolean favorite;
	}
	// 定義請求物件
	@Data
	public static class FavoriteReqDto {
		private Integer memberId;
		private Integer tripId;
		private boolean favorite;
	}


}
