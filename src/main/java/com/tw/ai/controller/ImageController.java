package com.tw.ai.controller;

import com.tw.ticket.service.impl.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@RestController
//public class ImageController {
//
//	private final ImageServiceImpl imageService;
//	@Autowired
//	public ImageController(ImageServiceImpl imageService) {
//		this.imageService = imageService;
//	}
//
//	// 所以這個url 就是專門存放圖片的?
//	// 是的 回傳byte[] 並定義MediaType.IMAGE_GIF_VALUE時
//	// 瀏覽器會接收 byte[] 並顯示成圖片
//	// 這段程式碼定義了一個從 HTTP 路徑 "/img/{imgUrl}" 獲取圖像的方法。當有人訪問這個路徑時，它將從 imageService 中查找 id 對應的圖像，並將該圖像作為 JPEG 圖像返回。
//	@RequestMapping(value = "/img/{imgUrl:[0-9]+}", produces = MediaType.IMAGE_GIF_VALUE)
//	public byte[] getPhoto(@PathVariable("imgUrl") final long id) {
//		return imageService.findImg(id);
//	}
//}
