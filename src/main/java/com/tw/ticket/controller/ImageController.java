package com.tw.ticket.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/TripLight")
public class ImageController {

	// @RequestMapping(value = "/getPhoto/{imgUrl:[a-zA-Z0-9_.]+}", produces = MediaType.IMAGE_JPEG_VALUE)
	// @ResponseBody
	// public byte[] getPhoto(@PathVariable("imgUrl") final String imgUrl) {
	// final File file = new File("D:/test.jpg");
	// final FileInputStream inputStream = new FileInputStream(file);
	// final byte[] bytes = new byte[inputStream.available()];
	// inputStream.read(bytes, 0, inputStream.available());
	// return bytes;
	// }
}
