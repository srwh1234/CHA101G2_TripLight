package com.tw.trip.service;

import com.tw.trip.dao.TripDao;
import com.tw.trip.pojo.Trip;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Transactional
@Service
public class TripSearchService {

        @PersistenceContext
        Session session;

        @Autowired
        TripDao tripDao;

        // ====== 一定要有圖片 ======
        public List<Trip> getTripListWithPic(Integer index){
            final String SQL = """
                    SELECT t.trip_id, t.trip_name, t.trip_day, t.city, t.trip_content, tm.id FROM trip t
                    join (
                    		SELECT trip_id, MIN(id) AS min_image_id
                    		FROM trip_image
                    		GROUP BY trip_id
                    )sub ON t.trip_id = sub.trip_id
                    join trip_image tm on tm.id = sub.min_image_id
                    """;

            List<Object[]> resultList1 = session.createNativeQuery(SQL,Object[].class)
                    .setFirstResult((index - 1) * 5)
                    .setMaxResults(5)
                    .getResultList();

            List<Trip> tripList = new ArrayList<>();

            for(Object[] row : resultList1){
                Integer tripId = (Integer) row[0];
                String tripName = (String) row[1];
                Integer tripDay = (Integer) row[2];
                String city = (String) row[3];
                String tripContent = (String) row[4];
                Integer id = (Integer) row[5];

                Trip trip = new Trip(tripId, tripName, tripDay, city, tripContent, id);
                tripList.add(trip);
            }

            return tripList;

        }

        public List<Trip> getTripBySearchCity(String[] cities){
            final String SQL = """
                    SELECT t.trip_id, t.trip_name, t.trip_day, t.city, t.trip_content, tm.id FROM trip t
                    join (
                    		SELECT trip_id, MIN(id) AS min_image_id
                    		FROM trip_image
                    		GROUP BY trip_id
                    )sub ON t.trip_id = sub.trip_id
                    join trip_image tm on tm.id = sub.min_image_id
                    WHERE t.city IN (:cities)
                    """;

            List<Object[]> resultList = session.createNativeQuery(SQL,Object[].class)
                    .setParameterList("cities",cities)
                    .list();

            List<Trip> tripList = new ArrayList<>();

            for(Object[] row : resultList){
                Integer tripId = (Integer) row[0];
                String tripName = (String) row[1];
                Integer tripDay = (Integer) row[2];
                String city = (String) row[3];
                String tripContent = (String) row[4];
                Integer id = (Integer) row[5];

                Trip trip = new Trip(tripId, tripName, tripDay, city, tripContent, id);
                tripList.add(trip);
            }

            return tripList;
        }

        public List<Trip> getTripBySearchKeyword(String keyword){
        final String SQL = """
                    SELECT t.trip_id, t.trip_name, t.trip_day, t.city, t.trip_content, tm.id FROM trip t
                    join (
                    		SELECT trip_id, MIN(id) AS min_image_id
                    		FROM trip_image
                    		GROUP BY trip_id
                    )sub ON t.trip_id = sub.trip_id
                    join trip_image tm on tm.id = sub.min_image_id
                    WHERE t.trip_name LIKE :keyword
                    """;

        List<Object[]> resultList = session.createNativeQuery(SQL,Object[].class)
                .setParameter("keyword", "%" + keyword +"%")
                .list();

        List<Trip> tripList = new ArrayList<>();

        for(Object[] row : resultList){
            Integer tripId = (Integer) row[0];
            String tripName = (String) row[1];
            Integer tripDay = (Integer) row[2];
            String city = (String) row[3];
            String tripContent = (String) row[4];
            Integer id = (Integer) row[5];

            Trip trip = new Trip(tripId, tripName, tripDay, city, tripContent, id);
            tripList.add(trip);
        }

        return tripList;
    }

         public List<Trip> getTripBySearchType(Integer[] types){
        final String SQL = """
                    SELECT t.trip_id, t.trip_name, t.trip_day, t.city, t.trip_content, tm.id FROM trip t
                    join (
                    		SELECT trip_id, MIN(id) AS min_image_id
                    		FROM trip_image
                    		GROUP BY trip_id
                    )sub ON t.trip_id = sub.trip_id
                    join trip_image tm on tm.id = sub.min_image_id
                    WHERE t.trip_type_id IN (:types)
                    """;

        List<Object[]> resultList = session.createNativeQuery(SQL,Object[].class)
                .setParameterList("types",types)
                .list();

        List<Trip> tripList = new ArrayList<>();

        for(Object[] row : resultList){
            Integer tripId = (Integer) row[0];
            String tripName = (String) row[1];
            Integer tripDay = (Integer) row[2];
            String city = (String) row[3];
            String tripContent = (String) row[4];
            Integer id = (Integer) row[5];

            Trip trip = new Trip(tripId, tripName, tripDay, city, tripContent, id);
            tripList.add(trip);
        }

        return tripList;
    }

        public List<Trip> getTripOrderByPrice(Integer index){
        final String SQL = """
                    SELECT t.trip_id, t.trip_name, t.trip_day, t.city, t.trip_content, tm.id FROM trip t
                    join (
                    		SELECT trip_id, MIN(id) AS min_image_id
                    		FROM trip_image
                    		GROUP BY trip_id
                    )sub ON t.trip_id = sub.trip_id
                    join trip_image tm on tm.id = sub.min_image_id
                    ORDER BY price_adult
                    """;

        List<Object[]> resultList = session.createNativeQuery(SQL,Object[].class)
                .setFirstResult((index - 1) * 5)
                .setMaxResults(5)
                .getResultList();

        List<Trip> tripList = new ArrayList<>();

        for(Object[] row : resultList){
            Integer tripId = (Integer) row[0];
            String tripName = (String) row[1];
            Integer tripDay = (Integer) row[2];
            String city = (String) row[3];
            String tripContent = (String) row[4];
            Integer id = (Integer) row[5];

            Trip trip = new Trip(tripId, tripName, tripDay, city, tripContent, id);
            tripList.add(trip);
        }

        return tripList;
    }



}
