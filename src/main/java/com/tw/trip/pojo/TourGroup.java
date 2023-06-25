package com.tw.trip.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.sql.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @Transient
    private String formattedStartDate;

    @Transient
    private String formattedEndDate;


}
