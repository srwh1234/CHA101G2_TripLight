package com.tw.trip.service;

import com.tw.member.model.Member;
import com.tw.trip.dao.TripDao;
import com.tw.trip.pojo.TourGroup;
import com.tw.trip.pojo.Trip;
import com.tw.trip.pojo.TripOrder;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class GroupOrderDetailService {

    @PersistenceContext
    Session session;

    @Autowired
    TripDao tripDao;

    public List<TripOrder> getOrderInfor(Integer tripOrderId){
        final String HQL = """
                SELECT new com.tw.trip.pojo.TripOrder(payDate, orderStatus, travelersAdult, travelersChildren, remarks) FROM TripOrder
                WHERE tripOrderId = :tripOrderId
                """;
        List<TripOrder> resultList = session.createQuery(HQL,TripOrder.class)
                .setParameter("tripOrderId", tripOrderId)
                .getResultList();

        return resultList ;
    }

    public Member getMemberInfor(Integer memberId){
        final String HQL = """
                SELECT new com.tw.member.model.Member(memberNameLast, memberNameFirst, memberPhone) FROM Member
                WHERE memberId= :memberId
                """;
        Member member = session.createQuery(HQL,Member.class)
                .setParameter("memberId", memberId)
                .uniqueResult();

        return member;

    }

    public TourGroup getTourGroupDate(Integer tripId, Integer tourGroupId) {

        final String HQL = """
                SELECT new com.tw.trip.pojo.TourGroup(startDate) FROM TourGroup
                WHERE tourGroupId = :tourGroupId
                """;

        TourGroup tourGroup = session.createQuery(HQL, TourGroup.class)
                .setParameter("tourGroupId", tourGroupId)
                .uniqueResult();


        // get tripDay from trip
        Trip trip = tripDao.findByPrimaryKey(tripId);
        Integer tripDay = trip.getTripDay();
        String tripName = trip.getTripName();

        // get startDate from tour_group
        LocalDate startDate = tourGroup.getStartDate().toLocalDate();

        // calculate days
        LocalDate calculatedDate = startDate.plusDays(tripDay);

        // format days
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = calculatedDate.format(formatter);

        // set and return
        tourGroup.setFormattedStartDate(formattedStartDate);
        tourGroup.setFormattedEndDate(formattedEndDate);
        tourGroup.setTripName(tripName);
        return tourGroup;

       }



    }
