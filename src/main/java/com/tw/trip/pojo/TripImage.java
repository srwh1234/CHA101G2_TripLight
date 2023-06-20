package com.tw.trip.pojo;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Data
public class TripImage{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int tripId;

    private byte[] image;

    private Timestamp uploadTime;

    @Transient
    private String imageBase64;

}
