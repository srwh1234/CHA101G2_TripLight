package com.tw.ai.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Entity
@Data
@IdClass(AiLocations.LocationPK.class)  // 關聯複合PK的Class
public class AiLocations{

    // 複合PK
    @Id
    private int aiFavoriteId;
    // 複合PK
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
