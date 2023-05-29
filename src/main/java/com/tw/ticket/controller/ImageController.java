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

	@RequestMapping(value = "/img/{imgUrl:[a-zA-Z0-9_.]+}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(@PathVariable("imgUrl") final long id) {

		return imageService.findImg(id);
	}
}
