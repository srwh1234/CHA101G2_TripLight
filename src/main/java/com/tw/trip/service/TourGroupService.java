package com.tw.trip.service;

import com.tw.trip.model.dao.TourGroupDao;
import com.tw.trip.model.dao.TripDaoImpl;
import com.tw.trip.model.pojo.TourGroup;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Transactional
@Service
public class TourGroupService {

    @Autowired
    TripDaoImpl tripDao;

    /*
    * @param tripId for tripDay
    * @param groupId for startDate
    */
    public TourGroup getTourGroupWithDates(Integer tripId, Integer groupId, TourGroupDao tourGroupDao) {

        TourGroup tourGroup = tourGroupDao.selectById(tripId);

        // get tripDay from trip
        Integer tripDay = tripDao.findByPrimaryKey(tripId).getTripDay();

        // get startDate from tour_group
        Date startDate = tourGroup.getStartDate();
        LocalDate localDate = startDate.toLocalDate();

        // calculate days
        LocalDate calculatedDate = localDate.plusDays(tripDay);

        // format days
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedStartDate = localDate.format(formatter);
        String formattedEndDate = calculatedDate.format(formatter);

        // return
        tourGroup.setFormattedStartDate(formattedStartDate);
        tourGroup.setFormattedEndDate(formattedEndDate);

        return tourGroup;
    }


}
