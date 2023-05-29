package com.tw.ai.entity.aIFavorite;


import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Entity
@Data
@IdClass(AiLocations.LocationPK.class)  // 關聯複合PK的Class
@Table(name = "ai_locations")
public class AiLocations{

    // 複合PK
    @Id
    @Column(name = "ai_favorite_id" ,nullable = false)
    private int aiFavoriteId;
    // 複合PK
    @Id
    @Column(name = "location_title" ,nullable = false)
    private String locationTitle;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Data
    public static class LocationPK implements Serializable {
        private int aiFavoriteId;
        private String locationTitle;
    }
}
