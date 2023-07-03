package com.tw.vendor.controller;

import java.io.IOException;
import java.sql.Timestamp;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.vendor.model.Trip2;
import com.tw.vendor.model.TripImage2;
import com.tw.vendor.service.TripImageService2;
import com.tw.vendor.service.TripService2;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/trip2")
@AllArgsConstructor
public class TripImageController2 {

	public static String IMG_URL = "/img/trip";

	// public final TripService2 tripService2 = null;

	private final TripImageService2 tripImageService2;
	private final TripService2 tripService2;

	// @GetMapping("/tripImage2")
	// public List<TripImage2> getTripImage(){
	// return tripImageService2.findAll();
	// }
	//
	//
	// @PostMapping("/tripImageadd")
	// public String processTripImage(@RequestBody TripImage2 tripImage2){
	// tripImageService2.save(tripImage2);
	// //System.out.println(tripImage2);
	// return "成功拿到資料";
	// }

	// // 用於指定 {imgUrl} 只能由數字組成。 [0-9] 表示匹配一個數字字符，+ 表示匹配前面的表達式一次或多次。
	// @GetMapping(value = "/img/trip2/{imgUrl:[0-9]+}", produces = MediaType.IMAGE_GIF_VALUE)
	// public byte[] getPhoto(@PathVariable("imgUrl") final int id) {
	// return tripService2.findImg(id);
	// }
	//
	// 上傳圖片 ()
	@PostMapping("/tripimgload")
	public boolean uploadImg(//
			@RequestParam("trip_image") final MultipartFile file, //
			@RequestParam("trip") final String json) {

		// System.out.println(json);

		// 以下都要放 service
		try {
			final Trip2 trip2 = new ObjectMapper().readValue(json, Trip2.class);
			tripService2.save(trip2);
			// System.out.println(trip2.getTripId());

			final TripImage2 img2 = new TripImage2();
			img2.setTripId(trip2.getTripId());
			img2.setImage(file.getBytes());
			img2.setUploadTime(new Timestamp(System.currentTimeMillis()));
			tripImageService2.save(img2);

		} catch (final IOException e) {
			e.printStackTrace();
		}
		return true;

	}
}
