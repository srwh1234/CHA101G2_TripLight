package com.tw.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tw.ticket.service.BkImageService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bk")
public class BkImageController {

	@Autowired
	private BkImageService bkImageService;

	/**
	 * 後台-票券資料管理-新增票券圖片 (FormData)
	 */
	@PostMapping("/addticketimageform")
	public String addTicketImageForm(	//
			@RequestParam("id") final int ticketId, @RequestPart("image") final MultipartFile file) {
		return bkImageService.addImage(ticketId, file);
	}

	//
	/**
	 * 後台-票券資料管理-刪除票券圖片
	 */
	@GetMapping("/removeticketimage")
	public boolean removeTicketImage(@RequestParam("imageId") final int imageId) {
		return bkImageService.removeImage(imageId);
	}
}
