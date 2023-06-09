package com.tw.contact.model_1;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class questReportImpl implements questReport{

    @Autowired
    private DataSource dataSource;
    @Override
    public void insert(questReportVO qRVO) {
        PreparedStatement ps = null;
        Connection con = null;
        try {

            con = dataSource.getConnection();
            ps = con.prepareStatement("INSERT INTO quest_report (member_id, employee_id, q_content, r_content, state, start_time, end_time, score ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1,qRVO.getMemberId());
            ps.setInt(2,qRVO.getEmployeeId());
            ps.setString(3,qRVO.getqContent());
            ps.setString(4,qRVO.getrContent());
            ps.setInt(5,qRVO.getState());
            ps.setTimestamp(6,Timestamp.valueOf(qRVO.getStartTime()));
            ps.setTimestamp(7,Timestamp.valueOf(qRVO.getEndTime()));
            ps.setInt(8,qRVO.getScore());

            ps.execute();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }

        }

    }
    @Override
    public void delete(int id) {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement("DELETE FROM quest_report where id = ?");
            ps.setInt(1,id);

            ps.execute();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }

        }


    }
    @Override
    public void update(questReportVO qRVO) {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement("UPDATE quest_report set member_id=?, employee_id=?, q_content=?, r_content=?, `state`=?, start_time=?, end_time=?, score=? where id = ?");
            ps.setInt(1,qRVO.getMemberId());
            ps.setInt(2,qRVO.getEmployeeId());
            ps.setString(3,qRVO.getqContent());
            ps.setString(4,qRVO.getrContent());
            ps.setInt(5,qRVO.getState());
            ps.setTimestamp(6,Timestamp.valueOf(qRVO.getStartTime()));
            ps.setTimestamp(7,Timestamp.valueOf(qRVO.getEndTime()));
            ps.setInt(8,qRVO.getScore());
            ps.setInt(9, qRVO.getId());

            ps.execute();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }

        }


    }
    @Override
    public List<questReportVO> getAll() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        questReportVO qRVO = null;
        List<questReportVO> list = new ArrayList<>();

        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement("SELECT * FROM quest_report ORDER BY id");
            rs =  ps.executeQuery();

            while(rs.next()) {
                qRVO = new questReportVO();
                qRVO.setId(rs.getInt("id"));
                qRVO.setMemberId(rs.getInt("member_id"));
                qRVO.setEmployeeId(rs.getInt("employee_id"));
                qRVO.setqContent(rs.getString("q_content"));
                qRVO.setrContent(rs.getString("r_content"));
                qRVO.setState(rs.getInt("state"));
                qRVO.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                qRVO.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                qRVO.setScore(rs.getInt("score"));
                list.add(qRVO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }

        }
        return list;
    }
}
