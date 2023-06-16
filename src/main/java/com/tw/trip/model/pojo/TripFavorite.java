package com.tw.trip.model.pojo;

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
    @Column(name="member_id")
    private Integer memberId;

    @Column(name="trip_id")
    private Integer tripId;

    @Column(name="add_time")
    private Timestamp addTime;
}
