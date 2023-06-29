package com.tw.vendor.dto;

import java.sql.Date;

import com.tw.trip.pojo.TourGroup;

import lombok.Data;

@Data
public class TourGroupManagementDto {
	
	private final int tourGroupId;
	private final int tripId;
	private final int priceAdult;
	private final int priceChild;
	private final Date startDate;
	private final int confirmedTravelersNo;
	private final int minTravelersNo;
	private final int maxTravelersNo;
	private final int status;
	
	public TourGroupManagementDto(final TourGroup tourGroup) {
		this.tourGroupId = tourGroup.getTourGroupId();
        this.tripId = tourGroup.getTripId();
        this.priceAdult = tourGroup.getPriceAdult();
        this.priceChild = tourGroup.getPriceChild();
        this.startDate = tourGroup.getStartDate();
        this.confirmedTravelersNo = tourGroup.getConfirmedTravelersNo();
        this.minTravelersNo = tourGroup.getMinTravelersNo();
        this.maxTravelersNo = tourGroup.getMaxTravelersNo();
        this.status = tourGroup.getStatus();
	
	}
}
