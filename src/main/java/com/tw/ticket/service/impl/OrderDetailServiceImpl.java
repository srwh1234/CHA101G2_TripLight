package com.tw.ticket.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.ticket.controller.OrderDetailController.DetailDto;
import com.tw.ticket.controller.OrderDetailController.DetailReqDto;
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
	private TicketOrderRepository orderRepository;

	@Autowired
	private TicketOrderDetailRepository orderDetailRepository;

	@Autowired
	private TicketCommentRepository commentRepository;

	/**
	 * 取得訂單明細清單
	 *
	 * @param memberId 會員編號
	 * @param orderId 訂單編號
	 * @return
	 */
	@Override
	public List<DetailDto> getItems(final int memberId, final int orderId) {
		final List<DetailDto> result = new ArrayList<>();

		final TicketOrder order = orderRepository.findById(orderId).orElse(null);

		// 查無資料
		if (order == null) {
			return result;
		}
		for (final TicketOrderDetail detail : order.getTicketOrderDetails()) {
			final DetailDto detailDto = new DetailDto();

			final TicketSn ticketSn = detail.getKey().getTicketSn();
			final Ticket ticket = ticketSn.getTicket();

			detailDto.setTicketId(ticket.getTicketId());
			detailDto.setName(ticket.getName());
			detailDto.setExpiryDate(ticket.getExpiryDate());

			detailDto.setTicketSnId(ticketSn.getTicketSnId());
			detailDto.setSerialNumber(ticketSn.getSerialNumber());

			detailDto.setPrice(detail.getUnitPrice());
			detailDto.setRefundStatus(detail.getRefundStatus());

			result.add(detailDto);
		}
		return result;
	}

	/**
	 * 退貨申請
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	@Override
	public boolean refundItem(final DetailReqDto reqDto) {
		final Member member = memberRepository.findById(reqDto.getMemberId()).orElse(null);
		if (member == null) {
			return false;
		}

		final TicketOrderDetail detail = //
				orderDetailRepository.findByKeyId(reqDto.getOrderId(), reqDto.getTicketSnId());

		if (detail == null // 正常的票券才能退
				|| detail.getRefundStatus() != TicketOrderDetail.REFUND_NONE) {
			return false;
		}
		detail.setRefundStatus(TicketOrderDetail.REFUND_REVIEW);
		detail.setRefundReason(reqDto.getRefundReason());

		orderDetailRepository.save(detail);
		return true;
	}

	/**
	 * 評價
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	@Override
	public boolean ratingItem(final DetailReqDto reqDto) {
		final Member member = memberRepository.findById(reqDto.getMemberId()).orElse(null);
		if (member == null) {
			return false;
		}

		final TicketOrderDetail detail = //
				orderDetailRepository.findByKeyId(reqDto.getOrderId(), reqDto.getTicketSnId());

		if (detail == null) {
			return false;
		}
		// 藏的有點深的票券編號
		final int ticketId = detail.getKey().getTicketSn().getTicket().getTicketId();

		final TicketComment comment = new TicketComment();
		comment.setTicketId(ticketId);
		comment.setMember(member);
		comment.setComment(reqDto.getComment());
		comment.setStatus(TicketComment.STATUS_SHOW);
		comment.setPostTime(Timestamp.from(Instant.now()));
		comment.setRating(reqDto.getRating());

		commentRepository.save(comment);
		return true;
	}
}
