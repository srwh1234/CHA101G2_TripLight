package com.tw.trip.service;

import com.tw.member.model.Member;
import com.tw.trip.dao.TripCommentDao;
import com.tw.trip.pojo.Trip;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class GroupOrderService {

    @PersistenceContext
    Session session;

    @Autowired
    TripCommentDao tripCommentDao;


    public Member getMemberInfor(Integer memberId){
        final String HQL = """
                SELECT new com.tw.member.model.Member(memberNameLast, memberNameFirst, memberPic) FROM Member
                WHERE memberId= :memberId
                """;
        Member member = session.createQuery(HQL,Member.class)
                .setParameter("memberId", memberId)
                .uniqueResult();

        return member;

    }

    // ====== 取得Group Order 頁面所需的資訊 ======
    public List<Trip> getTripsByMemberId(Integer memberIdArg){

        String sql = """
                select t.trip_id, trip_name, trip_content, o.trip_order_id, tm.id, tg.tour_group_id, t.trip_day from member m
                join trip_order o on m.member_id = o.member_id
                join tour_group tg on tg.tour_group_id = o.tour_group_id
                join trip t on t.trip_id = tg.trip_id
                join (
                    SELECT trip_id, MIN(id) AS min_image_id
                    FROM trip_image
                    GROUP BY trip_id
                ) sub ON t.trip_id = sub.trip_id
                join trip_image tm on tm.id = sub.min_image_id
                where m.member_id = :memberIdArg
                """;
        // 取得Result List
        List<Object[]> resultList = session.createNativeQuery(sql, Object[].class)
                .setParameter("memberIdArg", memberIdArg)
                .getResultList();

        List<Trip> tripList = new ArrayList<>();

        for(Object[] row : resultList ){

            Integer tripId = (Integer) row[0];
            String tripName = (String) row[1];
            String tripContent = (String) row[2];
            Integer tripOrderId = (Integer) row[3];
            Integer id = (Integer) row[4];
            Integer tourGroupId = (Integer) row[5];
            Integer tripDay = (Integer) row[6];

            Trip trip = new Trip(tripId, tripName, tripContent, tripOrderId, id, tourGroupId, tripDay);
            tripList.add(trip);
        }

        return tripList;

    }


}
