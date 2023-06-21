package com.tw.member.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tw.article.model.Article;
import com.tw.member.service.MyFavoriteService;
import com.tw.member.service.MyFavoriteService.TicketDTO;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.service.FavoriteService;
import com.tw.ticket.service.impl.ImageServiceImpl;

@RestController
public class MyFavoriteController {
	@Autowired
	private MyFavoriteService myFavoriteService;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private ImageServiceImpl ticketImageServiceImpl;

	// 載入票券
//	@GetMapping("/tickets/{id}")
//	public List<Ticket> ticketFavorites(@PathVariable("id") int id) {
//		System.out.println("Controller");
//		return myFavoriteService.getTicket(id);
//	}
	@GetMapping("/tickets/{id}")
	public List<TicketDTO> ticketFavorites(@PathVariable("id") int id) {
		System.out.println("Controller");
		return myFavoriteService.getTicket(id);
	}

	//票券圖片
//	@GetMapping(value = "/img/{imgUrl:[0-9]+}", produces = MediaType.IMAGE_GIF_VALUE)
//	public byte[] getPhoto(@PathVariable("imgUrl") final int id) {
//		return ticketImageServiceImpl.findImg(id);
//	}
//	@GetMapping(value = "/aimg/{imgUrl:[0-9]+}", produces = MediaType.IMAGE_GIF_VALUE)
//	public byte[] articleImg(@PathVariable("imgUrl") final int id) {
//
//		// 以下都要放 service
//		final Article article = articleRepository.findById(id).orElse(null);
//		// 沒有指定編號的圖片
//		if (article == null) {
//			final ClassPathResource resource = new ClassPathResource("images/bb.gif");
//
//			byte[] bytes = null;
//			try (final InputStream is = resource.getInputStream();) {
//				bytes = new byte[is.available()];
//				is.read(bytes);
//
//			} catch (final IOException e) {
//				e.printStackTrace();
//			}
//			return bytes;
//		}
//		return article.getArticlePicture();
//	}

	// 刪除票券
	 @PostMapping("/removeTicket")
	public boolean removeCart( 
			@RequestParam("memberId") final int memberId, 
			@RequestParam("ticketId") final int ticketId) {
		return myFavoriteService.removeItem(memberId, ticketId);
	}
	 
}
