package com.tw.trip.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourGroupDetail implements Serializable {
    public static final long serialVersionUID = 35L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer tourGroupId;

    private String travelerName;

    private String travelerPhone;

    private String travelerEmail;

    private Integer travelerGender;

    private Integer travelerAge;

    private Integer memberId;

}
