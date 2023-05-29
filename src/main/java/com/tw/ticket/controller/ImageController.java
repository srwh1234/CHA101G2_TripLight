package com.tw.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.service.impl.ImageServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/TripLight")
public class ImageController {

	@Autowired
	private ImageServiceImpl imageService;

	// 所以這個url 就是專門存放圖片的?
	// 這段程式碼定義了一個從 HTTP 路徑 "/img/{imgUrl}" 獲取圖像的方法。當有人訪問這個路徑時，它將從 imageService 中查找 id 對應的圖像，並將該圖像作為 JPEG 圖像返回。
	@RequestMapping(value = "/img/{imgUrl:[a-zA-Z0-9_.]+}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(@PathVariable("imgUrl") final long id) {

		return imageService.findImg(id);
	}
}
