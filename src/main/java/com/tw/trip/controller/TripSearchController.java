package com.tw.trip.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tw.trip.pojo.Trip;
import com.tw.trip.service.TripSearchService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

@RestController
@RequestMapping("/tripSearch")
public class TripSearchController {

    @Autowired
    TripSearchService tripSearchService;

    @GetMapping("/getTripList")
    public String getTripList() {
        final List<Trip> tripList = tripSearchService.getTripListWithPic();
        final String json = new Gson().toJson(tripList);

        return json;
    }

    @PostMapping("/getTripsBySearch")
    public String getTripsBySearch(final HttpServletRequest request) throws IOException {
        final BufferedReader reader = request.getReader();
        final StringBuilder stringBuilder = new StringBuilder();
        String dataRead;
        String json = null;

        // ====== 1. retrieved data from request n store in stringBuilder ======
        while ((dataRead = reader.readLine()) != null) {
            stringBuilder.append(dataRead);
        }

        reader.close();

        // ====== 2. parse to JSON via JSONObject ======
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(stringBuilder.toString());  // arguments for JSONObject is String
            // ====== 1. deal with TripOrder ======

            // ====== 2. deal with String[] cities ======
            String cities = jsonObject.getString("cities");
            String[] stringArray = new Gson().fromJson(cities, String[].class);
            for(String s: stringArray){
                System.out.println("test test"+ s);
            }

            List<Trip> tripList = tripSearchService.getTripBySearch(stringArray);

            json = new Gson().toJson(tripList);

        } catch (final JSONException e) {
            e.printStackTrace();

        }

        return json;
    }

}
