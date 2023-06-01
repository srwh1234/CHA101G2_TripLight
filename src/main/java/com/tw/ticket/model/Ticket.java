package com.tw.ticket.model;

import static com.tw.ticket.controller.ImageController.IMG_URL;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

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

	// cascade表示存檔時 也一起寫入TicketImage
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ticketId")
	private List<TicketImage> ticketImages = new ArrayList<>();

	// ----------------------------
	// 獲得指定索引的圖片路徑
	public String getImgUrlEx(final int index) {
		if (ticketImages.isEmpty()) {
			return IMG_URL + 0;
		}
		if (index >= ticketImages.size()) {
			return IMG_URL + 0;
		}
		final TicketImage img = ticketImages.get(index);
		return IMG_URL + img.getId();
	}

	// 獲得指定索引的圖片路徑的陣列
	public ArrayList<String> getImgUrlExs() {
		final ArrayList<String> result = new ArrayList<>();
		if (ticketImages.isEmpty()) {
			result.add(IMG_URL + 0);
			return result;
		}
		ticketImages.forEach(img -> {
			result.add(IMG_URL + img.getId());
		});
		return result;
	}

}
