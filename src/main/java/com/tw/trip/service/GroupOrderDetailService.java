package com.tw.trip.service;

import com.tw.trip.pojo.TripOrder;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GroupOrderDetailService {

    @PersistenceContext
    Session session;

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




}
