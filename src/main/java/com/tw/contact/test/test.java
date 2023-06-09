package com.tw.contact.test;

import com.tw.contact.model_1.chatRecordImpl;
import com.tw.contact.model_1.questReportImpl;
import com.tw.contact.model_1.questReportVO;
import com.tw.contact.model_1.chatRecordVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class test {

    private  questReportImpl qRImpl;
    @Test
    public void testInsert() {
        questReportVO qRVO = new questReportVO();
        qRVO.setMemberId(2);
        qRVO.setEmployeeId(2);
        qRVO.setqContent("AAA");
        qRVO.setrContent("BBB");
        qRVO.setState(1);
        qRVO.setStartTime(LocalDateTime.of(1111, 11, 11, 11, 11));
        qRVO.setEndTime(LocalDateTime.of(1111, 11, 11, 11, 11));
        qRVO.setScore(2);

        new questReportImpl().insert(qRVO);
        System.out.println("--------------------------成功---------------------------");
    }

    @Test
    public void testDelete() {
        new questReportImpl().delete(2);
        System.out.println("--------------------------成功---------------------------");
    }

    @Test
    public void testUpdate() {
        questReportVO qRVO = new questReportVO();
        qRVO.setMemberId(3);
        qRVO.setEmployeeId(3);
        qRVO.setqContent("BBB");
        qRVO.setrContent("CCC");
        qRVO.setState(2);
        qRVO.setStartTime(LocalDateTime.of(2222, 12, 12, 22, 22));
        qRVO.setEndTime(LocalDateTime.of(2222, 12, 12, 22, 22));
        qRVO.setScore(3);
        qRVO.setId(1);

        new questReportImpl().update(qRVO);

        System.out.println("--------------------------成功---------------------------");


    }

    @Test
    public void testQuery() {

        List list = qRImpl.getAll();
        for (Object li : list) {
            questReportVO qRVO = (questReportVO) li;
            System.out.println(qRVO.getId() + "," + qRVO.getMemberId() + "," + qRVO.getEmployeeId() + "," +
                    qRVO.getqContent() + "," + qRVO.getrContent() + "," + qRVO.getState() + "," +
                    qRVO.getStartTime().toString() + "," + qRVO.getEndTime().toString() + "," + qRVO.getScore());
        }
        System.out.println("--------------------------成功---------------------------");
    }

    @Test
    public void testInsert2() {
        chatRecordVO cRVO = new chatRecordVO();
        cRVO.setEmployeeId(3);
        cRVO.setMemberId(3);
        cRVO.setPostTime(LocalDateTime.of(2000, 2, 20, 20, 20));
        cRVO.setContent("AAAAAAAA");
        cRVO.setStatus(2);
        new chatRecordImpl().insert(cRVO);


    }

    @Test
    public void testDelete2(){
        chatRecordImpl cr = new chatRecordImpl();
        cr.delete(1);
    }

    @Test
    public void testUpdate2(){
        chatRecordVO cRVO = new chatRecordVO();
        cRVO.setId(3);
        cRVO.setEmployeeId(3);
        cRVO.setMemberId(3);
        cRVO.setPostTime(LocalDateTime.of(2000,02,02,02,02,02));
        cRVO.setContent("IOdsaojoqli");
        cRVO.setStatus(2);

        new chatRecordImpl().update(cRVO);

    }

    @Test
    public void testQuery2(){

        List<chatRecordVO> list = new chatRecordImpl().getAll();
        for(chatRecordVO e : list){
            System.out.println(e.getId() + "," + e.getEmployeeId() + "," + e.getMemberId() + "," + e.getMemberId() + "," + e.getPostTime().toString() + "," + e.getContent() + "," + e.getStatus());
        }

    }
}
