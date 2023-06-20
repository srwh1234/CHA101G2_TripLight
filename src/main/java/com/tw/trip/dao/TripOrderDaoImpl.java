package com.tw.trip.dao;

import com.tw.trip.pojo.TripOrder;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public class TripOrderDaoImpl implements TripOrderDao {

    @PersistenceContext
    private Session session;

    @Override
    public void insert(TripOrder tripOrder) {
        session.persist(tripOrder);
    }

    @Override
    public void update(TripOrder tripOrder) {
        session.merge(tripOrder);
        session.flush();
    }

    @Override
    public void deleteById(Integer id) {
        TripOrder tripOrder = session.get(TripOrder.class,id);
        if(tripOrder != null){
            session.remove(tripOrder);
        }
    }

    @Override
    public TripOrder findByPrimaryKey(Integer id) {
        return session.get(TripOrder.class,id);
    }

    @Override
    public List<TripOrder> getAll() {
        Query<TripOrder> query = session.createQuery("FROM TripOrder", TripOrder.class);
        return query.getResultList();
    }
}
