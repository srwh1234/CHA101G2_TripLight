package com.tw.trip.service;

import com.tw.trip.model.dao.TourGroupDao;
import com.tw.trip.model.dao.TourGroupDaoImpl;
import com.tw.trip.model.dao.TripDao;
import com.tw.trip.model.dao.TripDaoImpl;
import com.tw.trip.model.pojo.TourGroup;
import com.tw.trip.model.pojo.Trip;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Proxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Transactional
@Service
@Setter
@Getter
@Proxy(lazy = false)
public class TourGroupService {

    @Autowired
    TripDaoImpl tripDao;

    @Autowired
    TourGroupDaoImpl tourGroupDao;

    TourGroup tourGroup;

    private String formattedStartDate;
    private String formattedEndDate;

    public TourGroupService(){
        TripDaoImpl tripDao;
        TourGroupDaoImpl tourGroupDao;
    }

    /*
    * @param tripId for tripDay
    * @param groupId for startDate
    */
    public void getTourGroupDates(Integer tripId, Integer groupId) {

        // get tripDay from trip
        Integer tripDay = tripDao.findByPrimaryKey(tripId).getTripDay();

        // get startDate from tour_group
        Date startDate = tourGroupDao.selectById(groupId).getStartDate();
        LocalDate localDate = startDate.toLocalDate();

        // calculate days
        LocalDate calculatedDate = localDate.plusDays(tripDay);

        // format days
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedStartDate = localDate.format(formatter);
        String formattedEndDate = calculatedDate.format(formatter);

        // return
        this.formattedStartDate = formattedStartDate;
        this.formattedEndDate = formattedEndDate;


    }

    public void getTourGroupById(Integer tripId){
        tourGroup = tourGroupDao.selectById(tripId);
    }


}
