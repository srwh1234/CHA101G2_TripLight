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
@Table(name="trip_order", catalog="cha101_g2")
public class TripOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name= "trip_order_id")
    private Integer tripOrderId;
    @Column (name = "member_id")
    private Integer memberId;
    @Column (name = "tour_group_id")
    private Integer tourGroupId;
    @Column (name = "order_status")
    private Integer orderStatus;
    @Column (name = "pay_date")
    private Timestamp payDate;
    @Column (name = "pay_type")
    private String payType;
    @Column (name = "total_price")
    private Integer totalPrice;
    @Column (name = "travelers_adult")
    private Integer travelersAdult;
    @Column (name = "travelers_children")
    private Integer travelersChildren;
    @Column (name = "remarks")
    private String remarks;
    @Column (name = "employee_id")
    private Integer employeeId;
    @Column (name = "refund_status")
    private Integer refundStatus;
    @Column (name = "refund_reason")
    private String refundReason;

}
