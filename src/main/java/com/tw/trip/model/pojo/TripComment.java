package com.tw.trip.model.pojo;

import java.sql.Timestamp;

public class TripComment {
    private Integer id;
    private Integer tripId;
    private Integer memberId;

    private Integer rating;
    private String comment;
    private Integer status;

    private Timestamp postTime;
    private Integer editCount;
    private Timestamp lastEditTime;


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getTripId() {
        return tripId;
    }
    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }
    public Integer getMemberId() {
        return memberId;
    }
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Timestamp getPostTime() {
        return postTime;
    }
    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }
    public Integer getEditCount() {
        return editCount;
    }
    public void setEditCount(Integer editCount) {
        this.editCount = editCount;
    }
    public Timestamp getLastEditTime() {
        return lastEditTime;
    }
    public void setLastEditTime(Timestamp lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
