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
import com.tw.ticket.controller.BkOrderController.DetailDto;
import com.tw.ticket.controller.BkOrderController.OrderDto;
import com.tw.ticket.controller.BkOrderController.PageDto;
import com.tw.ticket.controller.BkOrderController.PageReqDto;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketOrder;
import com.tw.ticket.model.TicketOrderDetail;
import com.tw.ticket.model.dao.TicketOrderDetailRepository;
import com.tw.ticket.model.dao.TicketOrderRepository;
import com.tw.ticket.service.BkOrderService;
import com.tw.ticket.thirdparty.mail.MailService;

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

	@Autowired
	private MailService mailService;

	/**
	 * @param reqDto 請求參數
	 * @return 會員票券訂單分頁
	 */
	@Override
	public PageDto getItems(final PageReqDto reqDto) {
		final List<OrderDto> result = new ArrayList<>();

		final Pageable pageable = PageRequest.of(	//
				reqDto.getPage(),		// 查詢的頁數，從0起算
				reqDto.getSize()		// 查詢的每頁筆數
		);

		final Page<TicketOrder> page;

		// 如果只想找有退貨需求的訂單
		if (reqDto.isRefundChecked()) {
			page = repository.searchOrderByKeywordRefund(reqDto.getKeyword(), pageable);
		} else {
			page = repository.searchOrderByKeyword(reqDto.getKeyword(), pageable);
		}

		final PageDto pageDto = new PageDto();
		pageDto.setCurPage(reqDto.getPage());
		pageDto.setTotalPage(page.getTotalPages());

		for (final TicketOrder order : page.getContent()) {

			final Member member = memberRepository.findById(order.getMemberId()).orElse(null);

			if (member == null) {
				continue;
			}
			final OrderDto orderDto = new OrderDto();
			orderDto.setTicketOrderId(order.getTicketOrderId());
			orderDto.setMemberId(order.getMemberId());
			orderDto.setMemberName(member.getMemberNameLast() + member.getMemberNameFirst());
			orderDto.setTicketCount(order.getTicketOrderDetails().size());
			orderDto.setPayDate(order.getPayDate());
			orderDto.setPayType(order.getPayType());
			orderDto.setActualPrice(order.getActualPrice());

			pageDto.getOrders().add(orderDto);
		}

		return pageDto;
	}

	/**
	 * @param orderId 訂單編號
	 * @return 會員票券訂單明細
	 */
	@Override
	public List<DetailDto> getDetailItems(final int orderId) {
		final List<DetailDto> result = new ArrayList<>();
		final List<TicketOrderDetail> details = ticketOrderDetailRepository.findByTicketOrderId(orderId);

		for (final TicketOrderDetail detail : details) {
			final Ticket ticket = detail.getTicketSn().getTicket();

			final DetailDto response = new DetailDto();
			response.setTicketSnId(detail.getTicketSn().getTicketSnId());
			response.setName(ticket.getName());
			response.setPrice(detail.getUnitPrice());
			response.setExpiryDate(ticket.getExpiryDate());
			response.setRefundReason(detail.getRefundReason());
			response.setRefundStatus(detail.getRefundStatus());

			result.add(response);
		}
		return result;
	}

	/**
	 * 變更票卷訂單明細退貨狀態
	 *
	 * @param map 請求參數
	 * @return
	 */
	@Override
	public boolean updateItem(final Map<String, Object> map) {
		final int employeeId = (int) map.get("employeeId");
		final int orderId = (int) map.get("orderId");
		final int ticketSnId = (int) map.get("ticketSnId");
		final int status = (int) map.get("status");

		// 檢查訂單
		final TicketOrder order = repository.findById(orderId).orElse(null);
		if (order == null) {
			return false;
		}
		// 檢查會員
		final Member member = memberRepository.findById(order.getMemberId()).orElse(null);

		if (member == null) {
			return false;
		}

		// 檢查員工
		final Employee employee = employeeRepository.findById(employeeId).orElse(null);

		if (employee == null) {
			return false;
		}

		// 檢查票券狀態
		final TicketOrderDetail detail = ticketOrderDetailRepository //
				.findByTicketOrderIdAndTicketSnTicketSnId(orderId, ticketSnId);

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

		// 寄信
		sendRefundOkMail(member, detail, (status == 1));
		return true;
	}

	/**
	 * 寄出退款審核結果
	 *
	 * @param member 會員
	 * @param detail 訂單明細
	 * @param isOk 是否退款
	 */
	private void sendRefundOkMail(final Member member, final TicketOrderDetail detail, final boolean isOk) {
		final String to = member.getMemberEmail();

		// XXX 測試用 請不要寄給我
		// if (member.getMemberId() == 1) {
		// to = "srwh3577@gmail.com";
		// }
		final Ticket ticket = detail.getTicketSn().getTicket();

		final String subject = "TripLight退款通知";

		final String html = """
				<html><head><style>
				        table,tr,th,td {
				            border: 1px solid #09777d;
				            border-collapse: collapse;
				            padding: 2rem;
				            font-size: 1.5rem;
				        }
				        table { margin-top: 2rem; }
				        th {color: lavender;background-color: #26bec9; }
				        td {color: deeppink; min-width: 25rem; }
				    </style></head>
				<body><table>
				            <tr><th>訂單編號</th><td>%s</td></tr>
				            <tr><th>商品名稱</th><td>%s</td></tr>
				            <tr><th>金額</th><td>%d</td></tr>
				            <tr><th>退貨結果</th><td>%s</td></tr>
				        </table></body></html>""";

		final String text = String.format(html,		//
				detail.getTicketOrderId(),	//
				ticket.getName(),					//
				detail.getUnitPrice(),				//
				isOk ? "退款成功" : "無法退款"		//
		);

		mailService.sendHtmlEmail(to, subject, text);
	}
}
