package com.tw.trip.model.dao;

import com.tw.trip.model.pojo.TourGroup;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public class TourGroupDaoImpl implements TourGroupDao {

    @PersistenceContext
    private Session session;
    @Override
    public void insert(TourGroup tourGroup) {
        session.persist(tourGroup);
    }

    @Override
    public void update(TourGroup tourGroup) {
        session.merge(tourGroup);
        session.flush();
    }

    @Override
    public void deleteById(Integer id) {
        TourGroup tourGroup = session.get(TourGroup.class,id);
        if(tourGroup!= null){
            session.remove(tourGroup);
        }
    }

    @Override
    public TourGroup selectById(Integer id) {
        return session.get(TourGroup.class, id);
    }

    @Override
    public List<TourGroup> getAll() {
        Query<TourGroup> query = session.createQuery("FROM TourGroup", TourGroup.class);
        return query.getResultList();
    }
}
