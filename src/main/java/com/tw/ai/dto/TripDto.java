package com.tw.ai.dto;

import com.tw.trip.pojo.Trip;

import com.tw.vendor.model.Trip2;
import lombok.Data;

@Data
public class TripDto {

	private final int tripId;
	private final String tripName;
	private final int priceAdult;
	private final String tripDescription;
	private final int tripDay;
	private final String city;
	private final String tripImage;
	private final String tripContent;;

	public TripDto(final Trip2 trip2) {
		this.tripId = trip2.getTripId();
		this.tripName = trip2.getTripName();
		this.priceAdult = trip2.getPriceAdult();
		this.tripDescription = trip2.getTripDescription();
		this.tripDay = trip2.getTripDay();
		this.city = trip2.getCity();
		this.tripImage = trip2.getImgUrlEx(0);
		this.tripContent = trip2.getTripContent();
	}
}
