package com.tw.trip.service;

import com.tw.member.model.Member;
import com.tw.trip.dao.TripCommentDao;
import com.tw.trip.dao.TripCommentDaoImpl;
import com.tw.trip.pojo.TripComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupOrderService {

    @Autowired
    TripCommentDao tripCommentDao;



    public void addTripComment(Integer tripId, Integer memberId, Integer rating,
                                             String comment, Integer status, Integer editCount ){

        TripComment tripComment = new TripComment();
        tripComment.setTripId(tripId);

        tripComment.setMemberId(memberId);
        tripComment.setRating(rating);
        tripComment.setComment(comment);
        tripComment.setStatus(status);
        tripComment.setEditCount(editCount);
        tripCommentDao.insert(tripComment);

        System.out.println("trip comments successfully updated!");
    }


}
