package com.tw.member.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketFavorite;
import com.tw.ticket.model.TicketFavorite.PrimaryKey;
import com.tw.ticket.model.dao.TicketFavoriteRepository;
import com.tw.ticket.model.dao.TicketRepository;

@Service
public class MyFavoriteService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private TicketFavoriteRepository ticketFavoriteRepository;

	// 載入票券
	public List<Ticket> getTicket(int memberId) {
		final List<Ticket> result = new ArrayList<>();
		List<TicketFavorite> favorites = ticketFavoriteRepository.findByKeyMemberId(memberId);
		for (TicketFavorite f : favorites) {
			final Ticket t = f.getKey().getTicket();
			final Ticket detail = new Ticket();
			detail.setTicketId(t.getTicketId());
			detail.setName(t.getName());
			detail.setPrice(t.getPrice());
			detail.setDescription(t.getDescription());
			result.add(detail);
		}
		return result;
	}
//	//刪除票券

//	public boolean removeTicket( final Ticket ticketId) {
//		ticketFavoriteRepository.deleteByKeyTicket(ticketId);
//		return true;
//	}
//	   public void deleteByKeyTicket(PrimaryKey key) {
//	        ticketFavoriteRepository.deleteByKeyTicket(key);
//	    }
//	   
	public boolean removeItem(final int memberId, final int ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
		ticketFavoriteRepository.deleteById(new PrimaryKey(memberId, ticket));
		
		return true;
	}
}
