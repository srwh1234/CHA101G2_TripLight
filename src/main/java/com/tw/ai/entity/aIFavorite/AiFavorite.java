package com.tw.ai.entity.aIFavorite;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


// 建立實體
@Data
@Entity
@Table(name = "ai_favorite")
public class AiFavorite{
    // 設定欄位
    @Id
    @Column(name = "ai_favorite_id")
    private int aiFavoriteId;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "travel_days", nullable = false)
    private int travelDays;
    @Column(name = "people", nullable = false)
    private int people;
    @Column(name = "budget_range", nullable = false)
    private String budgetRange;

    @Column(name = "preferred_style")
    private String preferredStyle;

    @Column(name = "other_demands")
    private String otherDemands;

    @Column(name = "planning_description", nullable = false, columnDefinition = "TEXT")
    private String planningDescription;
    @Column(name = "route")
    private String route;

    @Column(name = "member_id")
    private int memberId;
}







