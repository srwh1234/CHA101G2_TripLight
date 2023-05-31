package com.tw.trip.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Trip implements Serializable {

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

    private int totalSales;

    private int priceAdult;

    private int priceChild;

    private int minTravelersNo;

    private int maxTravelersNo;

    private double ratingSum;

    private int ratingCount;

    private byte status;

    @Transient
    private String imgUrl;   // 圖片url

    // cascade表示存檔時 也一起寫入AiLocations
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tripId")
    private List<TripImage> tripImage = new ArrayList<>();

}