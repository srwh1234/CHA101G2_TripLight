package com.tw.trip.service;

import com.tw.trip.model.dao.TripCommentDao;
import com.tw.trip.model.dao.TripCommentDaoImpl;
import com.tw.trip.model.pojo.TripComment;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

public class TripCommentService {
    private TripCommentDao tripCommentDaoImpl = null;

    // ====== Test DAO ======
    /*public static void main(String[] args){

        TripCommentService tripCommentService = new TripCommentService();
        List<TripComment> tripComments = tripCommentService.getAll();
        Iterator iterator = tripComments.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }*/



    // ====== constructor get dao for accessing DB ======
    public TripCommentService(){
        tripCommentDaoImpl = new TripCommentDaoImpl();

    }

    // ====== pack parameters get from servlet and store VO ======
    // ====== VO as the argument to DAO and store to DB ======
    public TripComment addTripComment(Integer tripId, Integer memberId, Integer rating,
                                             String comment, Integer status, Integer editCount ){

        TripComment tripComment = new TripComment();
        tripComment.setTripId(tripId);
        tripComment.setMemberId(memberId);
        tripComment.setRating(rating);
        tripComment.setComment(comment);
        tripComment.setStatus(status);
        tripComment.setEditCount(editCount);
        tripCommentDaoImpl.insert(tripComment);

        return tripComment;
    }



    // ====== get one data by pk ======
    public TripComment getOneTripComment(Integer tripId){
        return tripCommentDaoImpl.findByPrimaryKey(tripId);
    }

    // ====== get all data from table ======
    public List<TripComment> getAll(){
        return tripCommentDaoImpl.getAll();
    }

    public List<TripComment> getTripComments(){
        return tripCommentDaoImpl.getTripComments();
    }
}
