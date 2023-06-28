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
import java.sql.Timestamp;
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
            final String SQL = """
                    SELECT concat(member_name_last, member_name_first) as memberName, member_pic, comment, post_time, rating from member m
                    join trip_comment tm on tm.member_id = m.member_id
                    Where trip_id = :tripId
                    ORDER BY post_time desc 
                    """;
            List<Object[]> resultList = session.createNativeQuery(SQL, Object[].class)
                    .setParameter("tripId", tripId)
                    .getResultList();

            List<TripComment> tripComments = new ArrayList<>();

            for(Object[] row : resultList){
                String memberName = (String) row[0];
                byte[] memberPic = (byte[]) row[1];
                String comment = (String) row[2];
                Timestamp postTime = (Timestamp) row[3];
                byte rating = (byte) row[4];


                TripComment tripComment = new TripComment(memberName, memberPic, comment, postTime, rating);
                tripComments.add(tripComment);

            }
            return tripComments;
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

    public Trip getTripByTripId(Integer tripId){
        final String HQL = """
                SELECT new com.tw.trip.pojo.Trip(tripName, tripDay, city, tripContent, tripDescription, tripNote) FROM Trip
                WHERE tripId= :tripId
                """;
        Trip trip = session.createQuery(HQL,Trip.class)
                .setParameter("tripId", tripId)
                .uniqueResult();

        return trip;

    }


}
