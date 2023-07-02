package com.tw.trip.pojo;

import static com.tw.trip.controller.TripImageController.IMG_URL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Trip implements Serializable {

	public static final long serialVersionUID = 3L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tripId;

	private int vendorId;

	private int tripTypeId;

	private String tripName;

	private String tripDescription;

	private String tripNote;

	private int tripDay;

	private String city;

	private int totalSales;  // 總銷售量

	private int priceAdult;

	private int priceChild;

	private int minTravelersNo;

	private int maxTravelersNo;

	private double ratingSum;

	private int ratingCount;

	private Integer status;

	private String tripContent;

	@Transient
	private String imageBase64;

	@Transient
	private String imageUrl;

	@Transient
	private Integer tripOrderId;

	@Transient
	private Integer id;

	@Transient
	private Integer tourGroupId;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "tripId")    // 這是啥? name不對也能接收喔? 好神奇
	private List<TripComment> tripComments = new ArrayList<>();

	// cascade表示存檔時 也一起寫入AiLocations
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "tripId")
	private List<TripImage> tripImage = new ArrayList<>();

	// ----------------------------
	// 獲得指定索引的圖片路徑
	public String getImgUrlEx(final int index) {
		if (index < 0 || index >= tripImage.size()) {
			return IMG_URL + 0;
		}
		return IMG_URL + tripImage.get(index).getId();   // 獲得第一個圖片的id
	}

	// 獲得指定索引的圖片路徑的陣列
	public ArrayList<String> getImgUrlExs() {
		final ArrayList<String> result = new ArrayList<>();
		if (tripImage.isEmpty()) {
			result.add(IMG_URL + 0);
			return result;
		}
		tripImage.forEach(img -> {
			result.add(IMG_URL + img.getId());
		});
		return result;
	}

	public Trip() {

	}

	public Trip(final Integer tripId, final String tripName, final Integer tripDay, final String city, final String tripContent, final byte[] image) {

		this.tripId = tripId;
		this.tripName = tripName;
		this.tripDay = tripDay;
		this.city = city;
		this.tripContent = tripContent;
		imageBase64 = Base64.getEncoder().encodeToString(image);

	}

	public Trip(final Integer tripId, final String tripName, final Integer tripDay, final String city, final String tripContent, final Integer id) {

		this.tripId = tripId;
		this.tripName = tripName;
		this.tripDay = tripDay;
		this.city = city;
		this.tripContent = tripContent;
		imageUrl = "/img/trips/" + id;

	}

	public Trip(final String tripName, final Integer tripDay, final String city, final String tripContent, final String tripDescription,
			final String tripNote) {
		this.tripName = tripName;
		this.tripDay = tripDay;
		this.city = city;
		this.tripContent = tripContent;
		this.tripDescription = tripDescription;
		this.tripNote = tripNote;
	}

	public Trip(final Integer tripId, final String tripName, final String tripContent, final Integer tripOrderId, final Integer id,
			final Integer tourGroupId, final Integer tripDay) {

		this.tripId = tripId;
		this.tripName = tripName;
		this.tripContent = tripContent;
		this.tripOrderId = tripOrderId;
		this.id = id;
		this.tourGroupId = tourGroupId;
		this.tripDay = tripDay;
	}
}