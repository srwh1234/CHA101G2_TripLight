package com.tw.trip.model.dao;


import com.tw.trip.model.pojo.TourGroup;
import com.tw.trip.model.pojo.TourGroupDetail;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public class TourGroupDetailDaoImpl implements TourGroupDetailDao{

    @PersistenceContext
    private Session session;


    @Override
    public void insert(TourGroupDetail tourGroupDetail) {
        session.persist(tourGroupDetail);
    }

    @Override
    public void update(TourGroupDetail tourGroupDetail) {
        session.merge(tourGroupDetail);
        session.flush();
    }

    @Override
    public void deleteById(Integer id) {
        TourGroupDetail tourGroupDetail = session.get(TourGroupDetail.class,id);
        if( tourGroupDetail != null){
            session.remove(tourGroupDetail);
        }
    }

    @Override
    public TourGroupDetail selectById(Integer id) {
        return session.get(TourGroupDetail.class,id);
    }

    @Override
    public List<TourGroupDetail> getAll() {
        Query<TourGroupDetail> query = session.createQuery("FROM TourGroupDetail",TourGroupDetail.class);
        return query.getResultList();
    }
}
