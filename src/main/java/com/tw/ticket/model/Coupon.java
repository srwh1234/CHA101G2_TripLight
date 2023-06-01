package com.tw.ticket.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer couponId;// 優惠券編號

	private String name;// 優惠券名稱

	private Date startDate;// 開始日期

	private Date expiryDate;// 到期日期

	private Integer discount;// 折扣金額
}
