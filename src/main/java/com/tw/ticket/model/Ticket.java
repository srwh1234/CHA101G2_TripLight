package com.tw.ticket.model;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;

	public static int DISABLED = 0;
	public static int ENABLED = 1;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ticketId;// 票券編號

	@OneToOne
	@JoinColumn(name = "ticket_type_id")
	private TicketType ticketType;// 票券類型編號

	private String name;// 名稱

	private Integer price;// 單價

	private Integer totalSales;// 累計銷售量

	private Integer status;// 狀態 (0:下架 1:上架)

	private Date expiryDate;// 到期日期

	private String description;// 票券描述 (50字以內)

	private String content;// 票券說明

	private String note;// 注意事項

	private String supplierName;// 供應商名稱

	private String city;// 縣市

	private String address;// 地址

	private Double latitude;// 緯度

	private Double longitude;// 經度

	private Integer ratingSum;// 累計評價分數

	private Integer ratingCount;// 評價人數

}
