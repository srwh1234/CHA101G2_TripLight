package com.tw.ai.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private String locationTitle;
    private double latitude;   //經度
    private double longitude;  //緯度
}

