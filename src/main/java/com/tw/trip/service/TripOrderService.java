package com.tw.trip.service;

import com.tw.trip.dao.TourGroupDao;
import com.tw.trip.dao.TourGroupDetailDao;
import com.tw.trip.dao.TripOrderDao;
import com.tw.trip.pojo.TourGroup;
import com.tw.trip.pojo.TourGroupDetail;
import com.tw.trip.pojo.TripComment;
import com.tw.trip.pojo.TripOrder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class TripOrderService {

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

}
