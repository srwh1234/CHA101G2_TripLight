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

    @PostMapping("/getTripsByKeyword")
    public String getTripsByKeyword(final HttpServletRequest request) throws IOException {
        final BufferedReader reader = request.getReader();
        final StringBuilder stringBuilder = new StringBuilder();
        String dataRead;
        String json = null;

        // ====== 1. retrieved data from request n store in stringBuilder ======
        while ((dataRead = reader.readLine()) != null) {
            stringBuilder.append(dataRead);
        }

        reader.close();

        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(stringBuilder.toString());  // arguments for JSONObject is String

            String keyword = jsonObject.getString("keyword");
            System.out.println(keyword);

            List<Trip> tripList = tripSearchService.getTripBySearchKeyword(keyword);
            json = new Gson().toJson(tripList);

        } catch (final JSONException e) {
            e.printStackTrace();

        }

        return json;
    }


    @PostMapping("/getTripsByCities")
    public String getTripsByCities(final HttpServletRequest request) throws IOException {
        final BufferedReader reader = request.getReader();
        final StringBuilder stringBuilder = new StringBuilder();
        String dataRead;
        String json = null;

        while ((dataRead = reader.readLine()) != null) {
            stringBuilder.append(dataRead);
        }

        reader.close();

        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(stringBuilder.toString());  // arguments for JSONObject is String

            String cities = jsonObject.getString("cities");
            System.out.println(cities);

            String[] citiesArr = new Gson().fromJson(cities, String[].class);

            List<Trip> tripList = tripSearchService.getTripBySearchCity(citiesArr);

            json = new Gson().toJson(tripList);

        } catch (final JSONException e) {
            e.printStackTrace();

        }

        return json;
    }

    @PostMapping("/getTripsByTypes")
    public String getTripsByTypes(final HttpServletRequest request) throws IOException {
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
            String types = jsonObject.getString("tripDayTypes");
            System.out.println(types);

            String[] typesArr = new Gson().fromJson(types, String[].class);
            Integer[] intArray = new Integer[typesArr.length];

            for (int i = 0; i < typesArr.length; i++) {
                intArray[i] = Integer.parseInt(typesArr[i]);
            }

            List<Trip> tripList = tripSearchService.getTripBySearchType(intArray);

            json = new Gson().toJson(tripList);

        } catch (final JSONException e) {
            e.printStackTrace();

        }

        return json;
    }



}
