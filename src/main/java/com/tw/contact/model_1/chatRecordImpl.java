package com.tw.contact.model_1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class chatRecordImpl implements chatRecord{

    @Autowired
    private DataSource dataSource;
    @Override
    public void insert(chatRecordVO cRVO) {
        Connection con = null;
        PreparedStatement ps = null;

        try{

            con = dataSource.getConnection();
            ps = con.prepareStatement("INSERT INTO chat_record(employee_id, member_id, post_time, content, status) VALUES (?, ?, ?, ?, ?)");

            ps.setInt(1, cRVO.getEmployeeId());
            ps.setInt(2, cRVO.getMemberId());
            ps.setTimestamp(3, Timestamp.valueOf(cRVO.getPostTime()));
            ps.setString(4,cRVO.getContent());
            ps.setInt(5, cRVO.getStatus());

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
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement("DELETE FROM chat_record where id = ?");
            ps.setInt(1,id);
            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public void update(chatRecordVO qRVO) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement("UPDATE chat_record set employee_id=?, member_id=?, post_time=?, content=?, status=? where id = ?");

            ps.setInt(1,qRVO.getEmployeeId());
            ps.setInt(2,qRVO.getMemberId());
            ps.setTimestamp(3,Timestamp.valueOf(qRVO.getPostTime()));
            ps.setString(4, qRVO.getContent());
            ps.setInt(5, qRVO.getStatus());
            ps.setInt(6,qRVO.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public List<chatRecordVO> getAll() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<chatRecordVO> list = new ArrayList<>();
        chatRecordVO cRVO = null;

        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement("SELECT * FROM chat_record order by id");
            rs = ps.executeQuery();

            while (rs.next()){
                cRVO = new chatRecordVO();
                cRVO.setId(rs.getInt("id"));
                cRVO.setEmployeeId(rs.getInt("employee_id"));
                cRVO.setMemberId(rs.getInt("member_id"));
                cRVO.setPostTime(rs.getTimestamp("post_time").toLocalDateTime());
                cRVO.setContent(rs.getString("content"));
                cRVO.setStatus(rs.getInt("status"));
                list.add(cRVO);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        return list;
    }
}
