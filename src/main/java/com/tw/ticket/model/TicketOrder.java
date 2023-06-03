package com.tw.ticket.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ticketOrderId;// 票券訂單編號

	private Integer memberId;// 擁有者的會員編號

	@ManyToOne
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;// 優惠券編號

	private Timestamp payDate;// 付款日期

	private String payType;// 付款方式 (信用卡/ATM/....)

	private Integer totalPrice;// 原始金額

	private Integer actualPrice;// 總金額

	// 逆向 cascade表示TicketOrder存檔時 也一起寫入ticketOrderDetails
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ticket_order_id")
	private List<TicketOrderDetail> ticketOrderDetails = new ArrayList<>();

}
