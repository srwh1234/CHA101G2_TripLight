package com.tw.ticket.model;

import java.io.Serializable;

import com.tw.employee.model.Employee;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketOrderDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	public static int REFUND_NONE = 0;
	public static int REFUND_REVIEW = 1;
	public static int REFUND_FINISH = 2;

	// 建構子 只是為了輸入方便
	public TicketOrderDetail(final Integer ticketOrderId, final TicketSn ticketSn) {
		this.key = new PrimaryKey(ticketOrderId, ticketSn);
	}

	@EmbeddedId
	private PrimaryKey key;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;// 退款處理的員工編號

	private Integer unitPrice;// 付款單價

	private Integer refundStatus;// 退款狀態 (0:無退款 1:退款中 2:退款完成)

	private String refundReason;// 退款原因

	@Embeddable
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PrimaryKey implements Serializable {
		private static final long serialVersionUID = 1L;

		private Integer ticketOrderId;// 票券訂單編號

		@ManyToOne
		@JoinColumn(name = "ticket_sn_id")
		private TicketSn ticketSn;// 票券序號編號
	}
}
