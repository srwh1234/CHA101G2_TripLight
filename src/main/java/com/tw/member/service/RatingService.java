package com.tw.member.service;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.ticket.model.TicketOrder;
import com.tw.ticket.model.dao.TicketOrderRepository;
import com.tw.trip.pojo.TripOrder;
import com.tw.trip.repository.TripOrderRepository;

@Service
public class RatingService {
	@Autowired
	private TicketOrderRepository ticketOrderRepository;
	@Autowired
	private TripOrderRepository tripOrderRepository;

	// 計算單一會員訂單數
//	
//	public int sum(int id) {
//	    List<TicketOrder> result = ticketOrderRepository.findByMemberId(id);
//	    List<TicketOrder> filteredList = new ArrayList<>();
//	    
//	    
//	    for (TicketOrder order : result) {
//	        if (order.getMemberId() == id) {
//	            filteredList.add(order);
//	        }
//	    }
////	    System.out.println(filteredList.size());
//	    return filteredList.size();
//	}

	// 計算具有相同 memberId 的票券訂單數量
	public int sum(int memberId) {
		List<TicketOrder> result = ticketOrderRepository.findByMemberId(memberId);
		return result.size();
	}
	// 計算具有相同 memberId 的旅行團訂單數量
	public int sum2(int memberId) {
		List<TripOrder> result = tripOrderRepository.findByMemberId(memberId);
		return result.size();
	}
	
}
