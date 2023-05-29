package com.tw.ai.service.aiService;


import com.tw.ai.common.GetMethod;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AiFormData implements GetMethod {

    private int id;
    private String destination;
    private int travelDays;
    private int people;
    private String budgetRange;
    private String preferredStyle;
    private String sightseeingValues;
    private String activitiesValues;
    private String otherDemands;


    public void setData(String formData) {
        destination = extractValue(formData, "1.目的地:");
        travelDays = Integer.parseInt(extractValue(formData, "2.旅行天數:"));
        people = Integer.parseInt(extractValue(formData, "3.人數:"));
        budgetRange = extractValue(formData, "4.預算:");
        preferredStyle = extractValue(formData, "5.偏好旅遊風格:");
        sightseeingValues = extractValue(formData, "6.偏好景點:");
        activitiesValues = extractValue(formData, "7.偏好活動:");
        otherDemands = extractValue(formData, "8.其他需求：");
    }

}
