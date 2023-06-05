package com.tw.ai.dto;

import com.tw.trip.model.Trip;
import lombok.Data;

@Data
public class TripDto {

    private int tripId;
    private String tripName;
    private int priceAdult;
    private String tripDescription;
    private int tripDay;
    private String city;
    private String tripImage;


    public TripDto(final Trip trip) {
        this.tripId = trip.getTripId();
        this.tripName = trip.getTripName();
        this.priceAdult = trip.getPriceAdult();
        this.tripDescription = trip.getTripDescription();
        this.tripDay = trip.getTripDay();
        this.city = trip.getCity();
        this.tripImage = trip.getImgUrlEx(0);
    }
}

