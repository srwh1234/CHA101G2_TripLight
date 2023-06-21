package com.tw.member.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketFavorite;
import com.tw.ticket.model.TicketFavorite.PrimaryKey;
import com.tw.ticket.model.TicketImage;
import com.tw.ticket.model.dao.TicketFavoriteRepository;
import com.tw.ticket.model.dao.TicketImageRepository;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.service.ImageService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
public class MyFavoriteService {

	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private TicketFavoriteRepository ticketFavoriteRepository;
	@Autowired
	private TicketImageRepository ticketImageRepository;
	@Autowired
	private ImageService imageService;
	// 拿到圖片URL
	public static String IMG_URL = "http://localhost:8080/img/";

	// 載入票券
//	public List<Ticket> getTicket(int memberId) {
//		final List<Ticket> result = new ArrayList<>();
//		List<TicketFavorite> favorites = ticketFavoriteRepository.findByKeyMemberId(memberId);
//		for (TicketFavorite f : favorites) {
//			final Ticket t = f.getKey().getTicket();
//			final Ticket detail = new Ticket();
//			detail.setTicketId(t.getTicketId());
//			detail.setName(t.getName());
//			detail.setPrice(t.getPrice());
//			detail.setDescription(t.getDescription());
//			result.add(detail);
//		}
//		return result;
//	}
	public List<TicketDTO> getTicket(int memberId) {
		final List<TicketDTO> result = new ArrayList<>();
		List<TicketFavorite> favorites = ticketFavoriteRepository.findByKeyMemberId(memberId);
		for (TicketFavorite f : favorites) {
			final Ticket t = f.getKey().getTicket();
			final TicketDTO detail = new TicketDTO();
			detail.setTicketId(t.getTicketId());
			detail.setName(t.getName());
			detail.setPrice(t.getPrice());
			detail.setDescription(t.getDescription());
			// List<Integer> image =
			// ticketImageRepository.findIdsByTicketId(t.getTicketId());
			
			//TicketImage image = ticketImageRepository.findByTicketId(t.getTicketId());
			//detail.setImage(image);
			
			detail.setImgUrl(imageService.findImgUrl(t.getTicketId()));
			
			result.add(detail);
		}
		System.out.println(result);
		return result;
	}

//public String findImgUrl(final int ticketId) {
//		final List<Integer> arrays = ticketImageRepository.findIdsByTicketId(ticketId);
//
//		if (arrays.isEmpty()) {
//			return IMG_URL + 0;
//		}
//		return IMG_URL + arrays.get(0);
//	}
	@Data
	@AllArgsConstructor
	public class TicketDTO {
		public TicketDTO() {
			// TODO Auto-generated constructor stub
		}

		public TicketImage getImage() {
			return image;
		}

		public void setImage(TicketImage image) {
			this.image = image;
		}

		private Integer ticketId;
		private String name;
		private Integer price;
		private String description;
		private TicketImage image;
		
		private String imgUrl;
	}
//	public byte[] getTicketImg(int id){
//		final TicketImage ticket = ticketImageRepository.findById(id).orElse(null);
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
//	}

//	//刪除票券  
	public boolean removeItem(final int memberId, final int ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
		ticketFavoriteRepository.deleteById(new PrimaryKey(memberId, ticket));

		return true;
	}

}
