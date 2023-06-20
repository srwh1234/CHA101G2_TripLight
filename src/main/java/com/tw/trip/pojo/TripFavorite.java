package com.tw.trip.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table (name="trip_favorite", catalog="cha101_g2")
public class TripFavorite {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer memberId;

    private Integer tripId;

    private Timestamp addTime;
}
