package com.tw.ticket.model.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.Coupon;
import com.tw.ticket.model.TicketOrder;

@Repository
public interface TicketOrderRepository extends JpaRepository<TicketOrder, Integer> {

	// 找出同一個Member的TicketOrder
	public List<TicketOrder> findByMemberId(int memberId);

	// 找出同一個Member的TicketOrder
	public Page<TicketOrder> findByMemberId(int memberId, Pageable pageable);

	// 找出使用優惠券的紀錄
	public TicketOrder findByMemberIdAndCoupon(int memberId, Coupon coupon);
}