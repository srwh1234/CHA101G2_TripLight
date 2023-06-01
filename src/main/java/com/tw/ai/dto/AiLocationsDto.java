package com.tw.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AiLocationsDto {
    private String locationTitle;
    private double latitude;   //經度
    private double longitude;  //緯度
}

