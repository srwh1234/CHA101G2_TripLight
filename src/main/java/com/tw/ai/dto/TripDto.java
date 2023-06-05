package com.tw.ai.dto;

import com.tw.trip.model.Trip;
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

