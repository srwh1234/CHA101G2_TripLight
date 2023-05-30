package com.tw.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.service.impl.ImageServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/TripLight")
public class ImageController {

	// XXX http://localhost:8080 VsCode測試才要加
	// 獲得圖片的URL
	public static String IMG_URL = "http://localhost:8080/TripLight/img/";

	@Autowired
	private ImageServiceImpl imageService;

	@GetMapping(value = "/img/{imgUrl:[0-9]+}", produces = MediaType.IMAGE_GIF_VALUE)
	public byte[] getPhoto(@PathVariable("imgUrl") final long id) {
		return imageService.findImg(id);
	}
}
