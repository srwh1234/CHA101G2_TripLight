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
@Table(name="trip_order", catalog="cha101_g2")
public class TripOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tripOrderId;

    private Integer memberId;

    private Integer tourGroupId;

    private Integer orderStatus;

    private Timestamp payDate;

    private String payType;

    private Integer totalPrice;

    private Integer travelersAdult;

    private Integer travelersChildren;

    private String remarks;

    private Integer employeeId;

    private Integer refundStatus;

    private String refundReason;

}
