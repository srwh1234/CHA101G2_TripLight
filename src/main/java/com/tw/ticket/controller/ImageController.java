package com.tw.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.service.impl.ImageServiceImpl;

@CrossOrigin(origins = "*")
@RestController
public class ImageController {

	@Autowired
	private ImageServiceImpl imageService;

	@GetMapping(value = "/img/{imgUrl:[0-9]+}", produces = MediaType.IMAGE_GIF_VALUE)
	public byte[] getPhoto(@PathVariable("imgUrl") final int id) {
		return imageService.findImg(id);
	}
}
