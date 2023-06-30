package com.tw.trip.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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

    private Integer paymentStatus;

    private String refundReason;


    @Transient
    private String formattedPayDate;

    public TripOrder(Timestamp payDate, Integer orderStatus, Integer travelersAdult, Integer travelersChildren, String remarks, Integer paymentStatus){

        this.paymentStatus = paymentStatus;

        // ====== 付款碼 0 代表未付款，此時payDay 會是null ======

        if(paymentStatus == 0){
            this.orderStatus = orderStatus;
            this.travelersAdult = travelersAdult;
            this.travelersChildren = travelersChildren;
            this.remarks = remarks;

        }else {
            // 創建 SimpleDateFormat 物件並設置日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // 使用 SimpleDateFormat 將 Timestamp 轉換為格式化的日期字符串
            formattedPayDate = sdf.format(payDate);

            this.orderStatus = orderStatus;
            this.travelersAdult = travelersAdult;
            this.travelersChildren = travelersChildren;
            this.remarks = remarks;
        }

    }




}
