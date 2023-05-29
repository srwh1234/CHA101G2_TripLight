package com.tw.ai.common;

import org.json.JSONObject;

public class Location {
    private String locationTitle;
    private double latitude;   //經度
    private double longitude;  //緯度

    public Location(String locationTitle,double latitude, double longitude) {

        this.locationTitle = locationTitle;

        this.latitude = latitude;

        this.longitude = longitude;

    }

    public void setLocationTitle(String locationTitle) {
        this.locationTitle = locationTitle;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        try {
            JSONObject json = new JSONObject();

            json.put("lat", latitude);
            json.put("lng", longitude);
            return json.toString();
        } catch (Exception e) {

        }
        return null;
    }


    public String getLocationTitle() {
        return locationTitle;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

