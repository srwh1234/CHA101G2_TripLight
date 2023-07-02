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
	
	@Autowired
	private MemberRepository memberRepository;

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
	
	//會員分級存回資料庫
	public void upgradeRating (int id, int memberGrade) {
		Member member = memberRepository.findByMemberId(id);
		if(member != null) {
			member.setMemberGrade(memberGrade);
			memberRepository.save(member);
		}
	}
}
