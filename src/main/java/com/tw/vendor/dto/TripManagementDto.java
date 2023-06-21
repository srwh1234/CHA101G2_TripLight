package com.tw.vendor.dto;

import com.tw.trip.pojo.Trip;
import lombok.Data;

@Data
public class TripManagementDto {

    private final int vendorId;
    private final int tripId;
    private final String tripName;
    private final int priceAdult;
    private final int priceChild;
    private final String tripDescription;
    private final int tripDay;
    private final String city;
    private final String tripContent;
    private final double ratingSum;
    private final double ratingCount;
    private final double totalSales;
    private final int status;



    public TripManagementDto(final Trip trip) {
        this.vendorId = trip.getVendorId();
        this.tripId = trip.getTripId();
        this.tripName = trip.getTripName();
        this.priceAdult = trip.getPriceAdult();
        this.priceChild = trip.getPriceChild();
        this.tripDescription = trip.getTripDescription();
        this.tripDay = trip.getTripDay();
        this.city = trip.getCity();
        this.ratingSum = trip.getRatingSum();
        this.ratingCount = trip.getRatingCount();
        this.tripContent = trip.getTripContent();
        this.totalSales = trip.getTotalSales();
        this.status = trip.getStatus();
    }
}
