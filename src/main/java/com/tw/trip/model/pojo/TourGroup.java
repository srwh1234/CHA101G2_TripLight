package com.tw.trip.model.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Setter
@Getter
@Proxy(lazy = false)
public class TourGroup implements Serializable {
    public static final long serialVersionUID = 33L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tourGroupId;
    private Integer tripId;
    private Integer priceAdult;
    private Integer priceChild;
    private Date startDate;
    private Integer confirmedTravelersNo;
    private Integer minTravelersNo;
    private Integer maxTravelersNo;
    private Integer status;

}
