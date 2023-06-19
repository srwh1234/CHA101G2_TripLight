package com.tw.trip.service;

import com.tw.trip.model.dao.TourGroupDetailDao;
import com.tw.trip.model.dao.TripOrderDao;
import com.tw.trip.model.pojo.TourGroupDetail;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class TripOrderService {

    @Autowired
    TourGroupDetailDao tourGroupDetailDao;

    public void addTourGroupDetail(
            Integer tourGroupId, String travelerName, String travelerPhone,
            String travelerEmail, Integer travelerGender,Integer travelerAge ){

        TourGroupDetail tourGroupDetail = new TourGroupDetail();
        tourGroupDetail.setTourGroupId(tourGroupId);
        tourGroupDetail.setTravelerName(travelerName);
        tourGroupDetail.setTravelerPhone(travelerPhone);
        tourGroupDetail.setTravelerEmail(travelerEmail);
        tourGroupDetail.setTravelerAge(travelerAge);
        tourGroupDetail.setTravelerGender(travelerGender);
        tourGroupDetailDao.insert(tourGroupDetail);
        System.out.println("updated successfully!");

    }
}
