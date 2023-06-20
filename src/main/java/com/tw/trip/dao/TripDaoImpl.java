package com.tw.trip.dao;

import com.tw.trip.pojo.Trip;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public class TripDaoImpl implements TripDao {

    @PersistenceContext // JPA API for getting Session
    private Session session;

    @Override
    public void insert(Trip trip) {
        session.persist(trip);
    }

    @Override
    public void update(Trip trip) {
        session.merge(trip);
        session.flush();
    }

    @Override
    public void deleteById(Integer id) {
        Trip trip = session.get(Trip.class, id);
        if (trip != null){
            session.remove(trip);
        }
    }

    @Override
    public Trip findByPrimaryKey(Integer id) {
        return session.get(Trip.class, id); // get() only works for search by ID
    }

    @Override
    public List<Trip> getAll() {
        Query<Trip> query = session.createQuery("FROM Trip", Trip.class);
        return query.getResultList(); // get List<Trip>
    }
}
