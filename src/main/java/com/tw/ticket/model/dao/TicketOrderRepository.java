package com.tw.ticket.model.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

	// 找出指定關鍵字的訂單
	@Query("SELECT t FROM TicketOrder t WHERE t.memberId IN "//
			+ "(SELECT m.memberId FROM Member m WHERE m.memberNameLast LIKE %:keyword% OR m.memberNameFirst LIKE %:keyword%)")
	public Page<TicketOrder> searchOrderByKeyword(	//
			@Param("keyword") String keyword,					// 關鍵字
			Pageable pageable									// 分頁
	);

	// 找出指定關鍵字的訂單 且有退貨需求
	@Query("SELECT t FROM TicketOrder t WHERE t.memberId IN "//
			+ "(SELECT m.memberId FROM Member m WHERE m.memberNameLast LIKE %:keyword% OR m.memberNameFirst LIKE %:keyword%) "//
			+ "AND t.ticketOrderId IN "//
			+ "(SELECT d.ticketOrderId FROM TicketOrderDetail d WHERE d.refundStatus=1)")
	public Page<TicketOrder> searchOrderByKeywordRefund(	//
			@Param("keyword") String keyword,					// 關鍵字
			Pageable pageable									// 分頁
	);
}
