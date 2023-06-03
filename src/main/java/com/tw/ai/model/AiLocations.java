package com.tw.ai.model;


import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Entity
@Data
@IdClass(AiLocations.LocationPK.class)  // 關聯複合PK的Class
public class AiLocations{

    @Id
    private int aiFavoriteId;
    @Id
    private String locationTitle;
    private double latitude;
    private double longitude;

    @Data
    public static class LocationPK implements Serializable {
        private int aiFavoriteId;
        private String locationTitle;
    }
}
