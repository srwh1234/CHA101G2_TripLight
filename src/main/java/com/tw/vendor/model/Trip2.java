package com.tw.vendor.model;

import static com.tw.trip.controller.TripImageController.IMG_URL;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity  // 標示該類別為實體類別，用於映射資料庫
@Getter
@Setter
@ToString
@Table(name = "Trip")
public class Trip2 {  // 表格名稱
	
	@Id  // 標示id為主鍵
	private int tripId;

	private int vendorId;

	private int tripTypeId;

	private String tripName;

	private String tripDescription;

	private String tripNote;

	private int tripDay;

	private String city;

	private int totalSales;

	private int priceAdult;

	private int priceChild;

	private int minTravelersNo;

	private int maxTravelersNo;

	private double ratingSum;

	private int ratingCount;

	private byte status;
	
	private String tripContent;

	// cascade表示存檔時 也一起寫入AiLocations
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "tripId")
	private List<TripImage2> tripImage2 = new ArrayList<>();

	// ----------------------------
	// 獲得指定索引的圖片路徑
	public String getImgUrlEx(final int index) {
		if (index < 0 || index >= tripImage2.size()) {
			return IMG_URL + 0;
		}
		return IMG_URL + tripImage2.get(index).getId();   // 獲得第一個圖片的id
	}

	// 獲得指定索引的圖片路徑的陣列
	public ArrayList<String> getImgUrlExs() {
		final ArrayList<String> result = new ArrayList<>();
		if (tripImage2.isEmpty()) {
			result.add(IMG_URL + 0);
			return result;
		}
		tripImage2.forEach(img -> {
			result.add(IMG_URL + img.getId());
		});
		return result;
	}

}
