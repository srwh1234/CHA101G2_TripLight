package com.tw.ai.service.aiService;


import com.tw.ai.common.GetMethod;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AiFormData implements GetMethod {

    private int formId;
    private String destination;
    private int travelDays;
    private int people;
    private String budgetRange;
    private String preferredStyle;
    private String sightseeingValues;
    private String activitiesValues;
    private String otherDemands;



    public String toMessage() {
        return "1.目的地:" + destination + "\n" +
               "2.旅行天數:" + travelDays + "\n" +
               "3.人數:" + people + "\n" +
               "4.預算:" + budgetRange + "台幣\n" +
               "5.偏好旅遊風格:" + preferredStyle + "\n" +
               "6.偏好景點:" + sightseeingValues + "\n" +
               "7.偏好活動:" + activitiesValues + "\n" +
               "8.其他需求：" + otherDemands;
    }
}
