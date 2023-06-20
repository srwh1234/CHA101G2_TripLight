package com.tw.trip.service;

import com.tw.trip.dao.TripDao;
import com.tw.trip.pojo.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class TripSearchService {

        @Autowired
        TripDao tripDao;


        public List<Trip> getTripListWithPic(){
            List<Trip> tripList = tripDao.getAll();
            List<Trip> tripListSent = new ArrayList<>();

            for(int i=0; i<tripList.size(); i++){
                Trip trip = tripList.get(i);
                byte[] buffer = trip.getTripImage().get(0).getImage();
                String tripPicBase64 = Base64.getEncoder().encodeToString(buffer);
                trip.setImageBase64(tripPicBase64);
                tripListSent.add(trip);
            }

            return tripListSent;
        }

}
