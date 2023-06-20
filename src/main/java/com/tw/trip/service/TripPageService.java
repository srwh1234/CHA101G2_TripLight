package com.tw.trip.service;

import com.tw.member.model.Member;
import com.tw.trip.dao.TourGroupDao;
import com.tw.trip.dao.TripCommentDao;
import com.tw.trip.dao.TripDao;
import com.tw.trip.pojo.TourGroup;
import com.tw.trip.pojo.Trip;
import com.tw.trip.pojo.TripComment;
import com.tw.trip.pojo.TripImage;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class TripPageService {

    @PersistenceContext
    private Session session;

    @Autowired
    TripDao tripDao;

    @Autowired
    TripCommentDao tripCommentDao;

    @Autowired
    TourGroupDao tourGroupDao;


    public List<TourGroup> getTourGroupWithDates(Integer tripId) {

        List<TourGroup> tourGroupList = tourGroupDao.selectByTripId(tripId);
        List<TourGroup> tourGroupListSent = new ArrayList<>();

        // get tripDay from trip
        Integer tripDay = tripDao.findByPrimaryKey(tripId).getTripDay();

        for (TourGroup tourGroup:tourGroupList){
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
            tourGroupListSent.add(tourGroup);

        }
        return tourGroupListSent;
    }


    // ====== 評論區 ======
    public List<TripComment> getTripCommentsByTripId(Integer tripId){

        List<TripComment> tripComments = tripDao.findByPrimaryKey(tripId).getTripComments();
        List<TripComment> tripCommentsSent = new ArrayList<>();

        for (TripComment tripComment : tripComments){
            final String name = (tripComment.getMember().getMemberNameLast()) + (tripComment.getMember().getMemberNameFirst());
            final byte[] buffer = tripComment.getMember().getMemberPic();

            String memberPicBase64 = Base64.getEncoder().encodeToString(buffer);
            tripComment.setMemberPicBase64(memberPicBase64);
            tripComment.setName(name);
            tripCommentsSent.add(tripComment);
        }

        return tripCommentsSent;
    }

    // ====== tripPage 圖片 ======
    public List<TripImage> getTripPicsById(Integer tripId){

        List<TripImage> tripImageListSent = new ArrayList<>();
        List<TripImage> tripImageList = tripDao.findByPrimaryKey(tripId).getTripImage();

        // ? 為何不能共用 List
        for(TripImage tripImage : tripImageList){
            final byte[] buffer = tripImage.getImage();
            String tripPicBase64 = Base64.getEncoder().encodeToString(buffer);
            tripImage.setImageBase64(tripPicBase64);
            tripImageListSent.add(tripImage);
        }
        return tripImageListSent;
    }


}
