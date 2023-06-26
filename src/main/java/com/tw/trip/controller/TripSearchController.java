package com.tw.trip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tw.trip.pojo.TourGroupDetail;
import com.tw.trip.pojo.Trip;
import com.tw.trip.service.TripSearchService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tripSearch")
public class TripSearchController {

    @Autowired
    TripSearchService tripSearchService;

    @GetMapping("/getTripList")
    public String getTripList(){
        List<Trip> tripList = tripSearchService.getTripListWithPic();
        String json = new Gson().toJson(tripList);

        return json;
    }

    @PostMapping("/getTripsBySearch")
    public String getTripsBySearch(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String dataRead;
        String json=null;

        // ====== 1. retrieved data from request n store in stringBuilder ======
        while((dataRead=reader.readLine()) != null){
            stringBuilder.append(dataRead);
        }

        reader.close();

        // ====== 2. parse to JSON via JSONObject ======
        JSONObject jsonObject;

        try{
            jsonObject = new JSONObject(stringBuilder.toString());  // arguments for JSONObject is String
            // ====== 1. deal with TripOrder ======



            // ====== 2. deal with String[] cities ======
            String cities = jsonObject.getString("cities");
            System.out.println("test 2" + cities);

            ObjectMapper objectMapper = new ObjectMapper();
            String[] cityList = objectMapper.readValue(cities,String[].class);

            List<Trip> tripList = tripSearchService.getTripBySearch(cityList);
            for(Trip trip : tripList){
                System.out.println(trip.getCity());
            }

            json = new Gson().toJson(tripList);

        }catch (JSONException e){
            e.printStackTrace();

        }

        return json;
    }

}
