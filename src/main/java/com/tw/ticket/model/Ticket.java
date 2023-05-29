package com.tw.ticket.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ticketId;// 票券編號

	private Integer ticketTypeId;// 票券類型編號

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

	@Transient
	private String imgUrl;   // 圖片url

	// 逆向 cascade表示存檔時 也一起寫入TicketImage
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ticket_id")
	private List<TicketImage> ticketImages = new ArrayList<>();



}
