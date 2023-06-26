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
import com.tw.trip.pojo.Trip;
import com.tw.trip.pojo.TripFavorite;
import com.tw.trip.pojo.TripFavorite.PrimaryKey2;
import com.tw.trip.pojo.TripImage;
import com.tw.trip.repository.TripFavoriteRepository;
import com.tw.trip.repository.TripRepository;
import com.tw.trip.service.TripImageService;
import com.tw.trip.service.TripService;

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
	
	@Autowired 
	private TripRepository tripRepository;
	@Autowired
	private TripFavoriteRepository tripFavoriteRepository;
	@Autowired
	private TripService tripService;
	@Autowired
	private TripImageService tripImageService;

	//票券=========================================
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
			detail.setImgUrl(imageService.findImgUrl(t.getTicketId()));			
			result.add(detail);
		}
		return result;
	}

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

//	//刪除票券  
	public boolean removeItem(final int memberId, final int ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
		ticketFavoriteRepository.deleteById(new PrimaryKey(memberId, ticket));
		return true;
	}
	//旅行團=========================================
	public List<TripDTO> getTrip(int memberId) {
		final List<TripDTO> result = new ArrayList<>();
		List<TripFavorite> favorites = tripFavoriteRepository.findByKeyMemberId(memberId);
		for (TripFavorite f : favorites) {
			final Trip t = f.getKey().getTrip();
			final TripDTO detail = new TripDTO();
			detail.setTripId(t.getTripId());
			detail.setTripName(t.getTripName());
			detail.setPriceAdult(t.getPriceAdult());
			detail.setPriceChild(t.getPriceChild());
			detail.setTripContent(t.getTripContent());	
			detail.setImgUrl(tripImageService.findImgUrl(t.getTripId()));
			result.add(detail);
		}
		return result;
	}

	@Data
	@AllArgsConstructor
	public class TripDTO {
		public TripDTO() {
			// TODO Auto-generated constructor stub
		}

		public TripImage getImageBase64() {
			return imageBase64;
		}

		public void setImageBase64(TripImage image) {
			this.imageBase64 = image;
		}

		private Integer tripId;
		private String tripName;
		private Integer priceAdult;
		private Integer priceChild;
		private String tripContent;
		private TripImage imageBase64;
		
		private String imgUrl;
	}

//	//刪除
	public boolean removeTrip(final int memberId, final int tripId) {
		Trip trip = tripRepository.findById(tripId).orElse(null);
		tripFavoriteRepository.deleteById(new PrimaryKey2(memberId,trip));

		return true;
	}
}
