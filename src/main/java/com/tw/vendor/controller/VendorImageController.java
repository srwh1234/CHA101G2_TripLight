package com.tw.vendor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.vendor.model.VendorImage;
import com.tw.vendor.service.VendorImageService;

@CrossOrigin(origins = "*")
@RestController("vendorImageController")
@RequestMapping("/vendorimage")
public class VendorImageController {

	public static String IMG_URL = "/img/trips/";

	@Autowired
	private VendorImageService vendorImageService;

	@GetMapping("/vendorimage2")
	public List<VendorImage> getVendorImage() {
		return vendorImageService.findAll();
	}

	@PostMapping("/vendorimage3")
	public String processVendorImage(@RequestBody final VendorImage vendorImage) {
		vendorImageService.save(vendorImage);
		System.out.println(vendorImage);
		return "成功拿到資料";
	}

	// // 用於指定 {imgUrl} 只能由數字組成。 [0-9] 表示匹配一個數字字符，+ 表示匹配前面的表達式一次或多次。
	// @GetMapping(value = "/img/trips/{imgUrl:[0-9]+}", produces = MediaType.IMAGE_GIF_VALUE)
	// public byte[] getPhoto(@PathVariable("imgUrl") final int id) {
	// return tripService2.findImg(id);
	// }

}
