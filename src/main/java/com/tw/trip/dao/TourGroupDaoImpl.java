package com.tw.trip.dao;

import com.tw.trip.pojo.TourGroup;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
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

    @Override
    public List<TourGroup> selectByTripId(Integer tripId){
        // 查詢旅遊團狀況代碼是0，統計中，並且日期最早的(代表與現在距離時間最近)
        Query<TourGroup> query = session.createQuery("FROM TourGroup WHERE tripId = :tripId AND status = :status ORDER BY startDate",TourGroup.class)
                .setParameter("tripId",Integer.toString(tripId))
                .setParameter("status", 0);
        return query.getResultList();


    }
}
