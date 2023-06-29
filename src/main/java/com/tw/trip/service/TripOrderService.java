package com.tw.trip.service;

import com.tw.member.model.Member;
import com.tw.trip.dao.TourGroupDao;
import com.tw.trip.dao.TourGroupDetailDao;
import com.tw.trip.dao.TripOrderDao;
import com.tw.trip.pojo.TourGroup;
import com.tw.trip.pojo.TourGroupDetail;
import com.tw.trip.pojo.TripComment;
import com.tw.trip.pojo.TripOrder;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Transactional
@Service
public class TripOrderService {

    @PersistenceContext
    Session session;

    @Autowired
    TourGroupDao tourGroupDao;

    @Autowired
    TourGroupDetailDao tourGroupDetailDao;

    @Autowired
    TripOrderDao tripOrderDao;

    public void addTourGroupDetail(TourGroupDetail[] tourGroupDetails){

        for (TourGroupDetail tourGroupDetail1 : tourGroupDetails){
            tourGroupDetailDao.insert(tourGroupDetail1);
        }
        System.out.println("TourGroupDetail successfully inserted!");
    }

    public void addTripOrder(Integer memberId, Integer tourGroupId, Integer orderStatus,
                             String payType, Integer qtyChild, Integer qtyAdult, String remarks){

        TripOrder tripOrder = new TripOrder();
        tripOrder.setMemberId(memberId);
        tripOrder.setTourGroupId(tourGroupId);
        tripOrder.setOrderStatus(orderStatus);
        tripOrder.setPayType(payType);

        // ====== calculate total price ======
        TourGroup tourGroup = tourGroupDao.selectById(tourGroupId);
        Integer totalPrice = (qtyChild * tourGroup.getPriceChild()) + (qtyAdult * tourGroup.getPriceAdult());
        tripOrder.setTotalPrice(totalPrice);
        // ====== /calculate total price ======

        tripOrder.setTravelersChildren(qtyChild);
        tripOrder.setTravelersAdult(qtyAdult);
        tripOrder.setRemarks(remarks);

        tripOrderDao.insert(tripOrder);
        System.out.println("TripOrder successfully inserted!");


    }

    public TourGroup getTourGroupById(Integer tourGroupId){
        TourGroup tourGroup = tourGroupDao.selectById(tourGroupId);
        return tourGroup;
    }

    public Member getMemberInfor(Integer memberId){
        final String HQL = """
                SELECT new com.tw.member.model.Member(memberNameLast, memberNameFirst, memberPhone, memberEmail) FROM Member
                WHERE memberId= :memberId
                """;
        Member member = session.createQuery(HQL,Member.class)
                .setParameter("memberId", memberId)
                .uniqueResult();

        return member;

    }

    public Integer getTripOrderAmount(Integer tripOrderId){
        final String HQL = """
                SELECT totalPrice FROM TripOrder 
                WHERE tripOrderId= :tripOrderId
                """;
        Integer amount = session.createQuery(HQL,Integer.class)
                .setParameter("tripOrderId", tripOrderId)
                .uniqueResult();

        return amount;


    }

    public void updatePaymentStatus(Integer paymentStatus, Integer tripOrderId){
        final String HQL = """
                UPDATE TripOrder 
                SET paymentStatus = :paymentStatus, payDate=CURRENT_TIMESTAMP 
                WHERE tripOrderId = :tripOrderId
                """;

        session.createQuery(HQL)
                .setParameter("tripOrderId", tripOrderId)
                .setParameter("paymentStatus", paymentStatus)
                .executeUpdate();

        System.out.println("paymentStatus successfully updated!");

    }

}
