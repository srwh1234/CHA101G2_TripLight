package com.tw.trip.service;

import com.tw.member.model.Member;
import com.tw.trip.dao.TripCommentDao;
import com.tw.trip.dao.TripCommentDaoImpl;
import com.tw.trip.pojo.GroupOrderObj;
import com.tw.trip.pojo.TripComment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
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


    // ====== Group Order 新增評論 ======
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


    // ====== 取得Group Order 頁面所需的資訊 ======
    public List<GroupOrderObj> getTripsByMemberId(Integer memberIdArg){

        String sql = """
                select t.trip_id, trip_name, trip_content, o.trip_order_id, m.member_id, member_pic, concat(member_name_last, member_name_first) as memberName, tm.image, tg.tour_group_id from member m
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
                .setParameter("memberIdArg", memberIdArg).getResultList();

        List<GroupOrderObj> groupOrderObjList = new ArrayList<>();

        for(Object[] row : resultList ){

            Integer tripId = (Integer) row[0];
            String tripName = (String) row[1];
            String tripContent = (String) row[2];
            Integer tripOrderId = (Integer) row[3];
            Integer memberId = (Integer) row[4];
            byte[] memberPic = (byte[]) row[5];
            String memberName = (String) row[6];
            byte[] tripPic = (byte[]) row[7];
            Integer tourGroupId = (Integer) row[8];

            GroupOrderObj groupOrderObj = new GroupOrderObj(tripId, tripName, tripContent, tripOrderId, memberId, memberPic, memberName, tripPic,tourGroupId);
            groupOrderObjList.add(groupOrderObj);
        }

        return groupOrderObjList;

    }


}
