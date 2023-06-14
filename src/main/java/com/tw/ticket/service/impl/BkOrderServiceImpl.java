package com.tw.ticket.service.impl;

import static com.tw.ticket.model.TicketOrderDetail.REFUND_FINISH;
import static com.tw.ticket.model.TicketOrderDetail.REFUND_NONE;
import static com.tw.ticket.model.TicketOrderDetail.REFUND_REVIEW;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tw.employee.dao.EmployeeRepository;
import com.tw.employee.model.Employee;
import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.ticket.controller.BkOrderController.OrderDetailResponse;
import com.tw.ticket.controller.BkOrderController.OrderRequest;
import com.tw.ticket.controller.BkOrderController.OrderResponse;
import com.tw.ticket.controller.BkOrderController.PageResponse;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketOrder;
import com.tw.ticket.model.TicketOrderDetail;
import com.tw.ticket.model.dao.TicketOrderDetailRepository;
import com.tw.ticket.model.dao.TicketOrderRepository;
import com.tw.ticket.service.BkOrderService;

@Service
public class BkOrderServiceImpl implements BkOrderService {

	@Autowired
	private TicketOrderRepository repository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private TicketOrderDetailRepository ticketOrderDetailRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

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

	// 回傳會員票券訂單明細
	@Override
	public List<OrderDetailResponse> getDetailItems(final int orderId) {
		final List<OrderDetailResponse> result = new ArrayList<>();
		final List<TicketOrderDetail> details = ticketOrderDetailRepository.findByKeyTicketOrderId(orderId);

		for (final TicketOrderDetail detail : details) {
			final Ticket ticket = detail.getKey().getTicketSn().getTicket();

			final OrderDetailResponse response = new OrderDetailResponse();
			response.setTicketSnId(detail.getKey().getTicketSn().getTicketSnId());
			response.setName(ticket.getName());
			response.setPrice(detail.getUnitPrice());
			response.setExpiryDate(ticket.getExpiryDate());
			response.setRefundReason(detail.getRefundReason());
			response.setRefundStatus(detail.getRefundStatus());

			result.add(response);
		}
		return result;
	}

	// 變更票卷訂單明細退貨狀態
	@Override
	public boolean updateItem(final Map<String, Object> map) {
		final int employeeId = (int) map.get("employeeId");
		final int orderId = (int) map.get("orderId");
		final int ticketSnId = (int) map.get("ticketSnId");
		final int status = (int) map.get("status");

		// 檢查員工
		final Employee employee = employeeRepository.findById(employeeId).orElse(null);

		if (employee == null) {
			return false;
		}

		// 檢查票券狀態
		final TicketOrderDetail detail = ticketOrderDetailRepository //
				.findByKeyTicketOrderIdAndKeyTicketSnTicketSnId(orderId, ticketSnId);

		if (detail == null || detail.getRefundStatus() != REFUND_REVIEW) {
			return false;
		}

		// 退款成功
		if (status == 1) {
			detail.setRefundStatus(REFUND_FINISH);
		} else {
			detail.setRefundStatus(REFUND_NONE);
		}
		detail.setEmployee(employee);

		ticketOrderDetailRepository.save(detail);
		return true;
	}
}
