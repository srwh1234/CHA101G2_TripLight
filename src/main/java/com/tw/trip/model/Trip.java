package com.tw.trip.model;


import com.tw.ticket.model.TicketImage;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

import static com.tw.ticket.controller.ImageController.IMG_URL;

@Data
@Entity
public class Trip{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tripId;

    private int vendorId;

    private int tripTypeId;

    private String tripName;

    private String tripDescription;

    private String tripNote;

    private int tripDay;

    private String city;

    private int totalSales;

    private int priceAdult;

    private int priceChild;

    private int minTravelersNo;

    private int maxTravelersNo;

    private double ratingSum;

    private int ratingCount;

    private byte status;

    // cascade表示存檔時 也一起寫入AiLocations
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tripId")
    private List<TripImage> tripImage = new ArrayList<>();

    // ----------------------------
    // 獲得指定索引的圖片路徑
    public String getImgUrlEx(final int index) {
        if (index < 0 || index >= tripImage.size()) {
            return IMG_URL +"trip/"+ 0;
        }
        System.out.println(tripImage.get(index).getId());
        return IMG_URL +"trip/"+ tripImage.get(index).getId();   // 獲得第一個圖片的id
    }

}