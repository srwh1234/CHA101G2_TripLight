package com.tw.ticket.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.ticket.controller.BkOrderController.OrderRequest;
import com.tw.ticket.controller.BkOrderController.OrderResponse;
import com.tw.ticket.controller.BkOrderController.PageResponse;
import com.tw.ticket.model.TicketOrder;
import com.tw.ticket.model.dao.TicketOrderRepository;
import com.tw.ticket.service.BkOrderService;

@Service
public class BkOrderServiceImpl implements BkOrderService {

	@Autowired
	private TicketOrderRepository repository;

	@Autowired
	private MemberRepository memberRepository;

	// 回傳會員票券訂單
	@Override
	public PageResponse getItems(final OrderRequest request) {
		final List<OrderResponse> result = new ArrayList<>();

		final Pageable pageable = PageRequest.of(	//
				request.getPage(),		// 查詢的頁數，從0起算
				request.getSize()		// 查詢的每頁筆數
		);

		final Page<TicketOrder> page = repository.searchOrderByKeyword(//
				request.getKeyword(),	// 關鍵字
				pageable				// 分頁
		);

		final PageResponse response = new PageResponse();
		response.setCurPage(request.getPage());
		response.setTotalPage(page.getTotalPages());

		for (final TicketOrder order : page.getContent()) {

			final Member member = memberRepository.findById(order.getMemberId()).orElse(null);

			if (member == null) {
				continue;
			}
			final OrderResponse orderResponse = new OrderResponse();
			orderResponse.setTicketOrderId(order.getTicketOrderId());
			orderResponse.setMemberId(order.getMemberId());
			orderResponse.setMemberName(member.getMemberNameLast() + member.getMemberNameFirst());
			orderResponse.setTicketCount(order.getTicketOrderDetails().size());
			orderResponse.setPayDate(order.getPayDate());
			orderResponse.setPayType(order.getPayType());
			orderResponse.setActualPrice(order.getActualPrice());

			response.getOrders().add(orderResponse);
		}

		return response;
	}
}
