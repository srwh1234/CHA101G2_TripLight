package com.tw.ai.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



// 建立實體
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AiFavorite{
    // 設定欄位
    @Id
    private int aiFavoriteId;                 // 流水號
    private String destination;               // 目的地
    private int travelDays;                   // 旅遊天數
    private int people;                       // 人數
    private String budgetRange;               // 預算範圍
    private String preferredStyle;            // 喜好旅遊風格
    private String otherDemands;              // 其他要求
    private String planningDescription;       // AI規劃結果
    private String route;                     // 行程路線
    private int memberId;                     // 會員ID

    // cascade表示存檔時 也一起寫入AiLocations
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "aiFavoriteId")
//    private List<AiLocations> aiLocations = new ArrayList<>();
}







