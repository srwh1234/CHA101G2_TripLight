package com.tw.ticket.service.impl;

import static com.tw.ticket.model.TicketComment.STATUS_SHOW;
import static com.tw.ticket.model.TicketOrderDetail.REFUND_NONE;
import static com.tw.ticket.model.TicketOrderDetail.REFUND_REVIEW;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.ticket.controller.OrderDetailController.DetailRequest;
import com.tw.ticket.controller.OrderDetailController.DetailResponse;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketComment;
import com.tw.ticket.model.TicketOrder;
import com.tw.ticket.model.TicketOrderDetail;
import com.tw.ticket.model.TicketSn;
import com.tw.ticket.model.dao.TicketCommentRepository;
import com.tw.ticket.model.dao.TicketOrderDetailRepository;
import com.tw.ticket.model.dao.TicketOrderRepository;
import com.tw.ticket.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private TicketOrderRepository ticketOrderRepository;

	@Autowired
	private TicketOrderDetailRepository ticketOrderDetailRepository;

	@Autowired
	private TicketCommentRepository ticketCommentRepository;

	// 取得訂單明細清單
	@Override
	public List<DetailResponse> getItems(final int memberId, final int orderId) {
		final List<DetailResponse> result = new ArrayList<>();

		final TicketOrder order = ticketOrderRepository.findById(orderId).orElse(null);

		// 查無資料
		if (order == null) {
			return result;
		}
		for (final TicketOrderDetail detail : order.getTicketOrderDetails()) {
			final DetailResponse response = new DetailResponse();

			final TicketSn ticketSn = detail.getKey().getTicketSn();
			final Ticket ticket = ticketSn.getTicket();

			response.setTicketId(ticket.getTicketId());
			response.setName(ticket.getName());
			response.setExpiryDate(ticket.getExpiryDate());

			response.setTicketSnId(ticketSn.getTicketSnId());
			response.setSerialNumber(ticketSn.getSerialNumber());

			response.setPrice(detail.getUnitPrice());
			response.setRefundStatus(detail.getRefundStatus());

			result.add(response);
		}
		return result;
	}

	// 退貨申請
	@Override
	public boolean refundItem(final DetailRequest request) {
		final Member member = memberRepository.findById(request.getMemberId()).orElse(null);
		if (member == null) {
			return false;
		}

		final TicketOrderDetail detail = ticketOrderDetailRepository.findByKeyId(//
				request.getOrderId(),		//
				request.getTicketSnId()		//
		);

		if (detail == null || detail.getRefundStatus() != REFUND_NONE) {
			return false;
		}

		detail.setRefundStatus(REFUND_REVIEW);
		detail.setRefundReason(request.getRefundReason());

		ticketOrderDetailRepository.save(detail);
		return true;
	}

	// 評價
	@Override
	public boolean ratingItem(final DetailRequest request) {
		final Member member = memberRepository.findById(request.getMemberId()).orElse(null);
		if (member == null) {
			return false;
		}

		final TicketOrderDetail detail = ticketOrderDetailRepository.findByKeyId(//
				request.getOrderId(),		//
				request.getTicketSnId()		//
		);

		if (detail == null) {
			return false;
		}
		// 藍色蜘蛛網...
		final int ticketId = detail.getKey().getTicketSn().getTicket().getTicketId();

		final TicketComment comment = new TicketComment();
		comment.setTicketId(ticketId);
		comment.setMember(member);
		comment.setComment(request.getComment());
		comment.setStatus(STATUS_SHOW);
		comment.setPostTime(new Timestamp(System.currentTimeMillis()));
		comment.setRating(request.getRating());

		ticketCommentRepository.save(comment);
		return true;
	}
}
