package com.tw.trip.dao;


import com.tw.member.model.Member;
import com.tw.trip.pojo.TripComment;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class TripCommentDaoImpl implements TripCommentDao {

    @PersistenceContext
    private Session session;


    @Override
    public void insert(TripComment tripComment) {
        session.persist(tripComment);
    }

    @Override
    public void update(TripComment tripComment) {
        session.merge(tripComment);
        session.flush();
    }

    @Override
    public void deleteById(Integer id) {
        TripComment tripComment = session.get(TripComment.class,id);
        if(tripComment!=null){
            session.remove(tripComment);
        }
    }

    @Override
    public TripComment findByPrimaryKey(Integer id) {
        return session.get(TripComment.class,id);
    }

    @Override
    public List<TripComment> getAll() {
        Query<TripComment> query = session.createQuery("FROM TripComment",TripComment.class);
        return query.getResultList();
      }


}
