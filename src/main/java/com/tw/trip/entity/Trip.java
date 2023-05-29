package com.tw.trip.entity;


import jakarta.persistence.*;
import lombok.Data;

@Table(name = "trip")
@Data
@Entity
public class Trip{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id", nullable = false)
    private int tripId;
    @Column(name = "vendor_id", nullable = false)
    private int vendorId;
    @Column(name = "trip_type_id", nullable = false)
    private int tripTypeId;
    @Column(name = "trip_name", nullable = false)
    private String tripName;
    @Column(name = "trip_description", nullable = false)
    private String tripDescription;
    @Column(name = "trip_note")
    private String tripNote;
    @Column(name = "trip_day", nullable = false)
    private int tripDay;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "total_sales", nullable = false)
    private int totalSales;
    @Column(name = "price_adult", nullable = false)
    private int priceAdult;
    @Column(name = "price_child", nullable = false)
    private int priceChild;
    @Column(name = "min_travelers_no", nullable = false)
    private int minTravelersNo;
    @Column(name = "max_travelers_no", nullable = false)
    private int maxTravelersNo;
    @Column(name = "rating_sum", nullable = false)
    private double ratingSum;
    @Column(name = "rating_count", nullable = false)
    private int ratingCount;
    @Column(name = "status", nullable = false)
    private byte status;
    @Transient
    private String imgUrl;   // 圖片url

}