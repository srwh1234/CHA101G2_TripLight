package com.tw.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketImage;
import com.tw.ticket.service.TicketService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/TripLight")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@GetMapping("/rndtickets")
	public List<MappingResponse> randomTickets() {
		return ticketService.findRnd();
	}

	// 定義一個物件回傳
	@Data
	static public class MappingResponse {

		public MappingResponse(final Ticket ticket) {
			this.ticketId = ticket.getTicketId();
			this.name = ticket.getName();
			this.price = ticket.getPrice();
			this.rating = ticket.getRatingSum() / ticket.getRatingCount();
			this.ratingPerson = ticket.getRatingCount();
			this.city = ticket.getCity();
			this.description = ticket.getDescription();

			if (ticket.getTicketImages().isEmpty()) {
				this.image = String.format("https://picsum.photos/600/600/?random=%d", 0);
			} else {
				final TicketImage ticketImage = ticket.getTicketImages().get(0);
				this.image = String.format(//
						"https://picsum.photos/600/600/?random=%d", ticketImage.getId());
			}

		}

		private final int ticketId;
		private final String name;
		private final int price;
		private final int rating;
		private final int ratingPerson;
		private final String city;
		private final String description;
		private final String image;
	}
}
