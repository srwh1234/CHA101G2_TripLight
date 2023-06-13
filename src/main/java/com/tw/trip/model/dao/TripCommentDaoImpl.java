package com.tw.trip.model.dao;

import com.tw.trip.model.pojo.TripComment;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.tw.trip.util.Util;
public class TripCommentDaoImpl implements TripCommentDao {

    // ====== DB Infor. ======
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/db01?serverTimezone=Asia/Taipei";
    String userid = "root";
    String passwd = "aecl5505!";
    // ====== SQL Statement ======
    public static final String INSERT_STMT =
            "INSERT INTO cha101_g2.trip_comment(trip_id, member_id, rating, comment, status, edit_count) " +
                    "VALUES( ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_STMT =
            "UPDATE cha101_g2.trip_comment" + "SET trip_id = ?, member_id = ?, rating = ?, comment = ?, status = ?, post_time = ?, edit_count = ?, last_edit_time = ?"
                    + "WHERE id= ? ;";
    public static final String SELECT_ALL_STMT =
            "SELECT * FROM cha101_g2.trip_comment; ";

    public static final String SELECT_ONE_STMT =
            "SELECT * FROM cha101_g2.trip_comment WHERE id = ?;";

    public static final String SELECT_FOR_TRIPCOMMENT = "SELECT concat(member_name_last, member_name_first) AS name, member_pic, comment, last_edit_time FROM cha101_g2.member m " +
            "JOIN cha101_g2.trip_comment tc on tc.member_id =  m.member_id " +
            "ORDER BY last_edit_time DESC;";

    public static final String DELETE_STMT =
            "DELETE FROM cha101_g2.trip_comment WHERE id = ?;";
    @Override
    public void insert(TripComment tripComment) {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try{
            Class.forName(Util.DRIVER);
            con = DriverManager.getConnection(Util.URL, Util.USER_ID,Util.PASSWORD);
            preparedStatement = con.prepareStatement(INSERT_STMT);

            // set data in VO into DB
            preparedStatement.setInt(1, tripComment.getTripId());
            preparedStatement.setInt(2, tripComment.getMemberId());
            preparedStatement.setInt(3,tripComment.getRating());
            preparedStatement.setString(4,tripComment.getComment());
            preparedStatement.setInt(5,tripComment.getStatus());
            preparedStatement.setInt(6,tripComment.getEditCount());
            preparedStatement.executeUpdate();

        }catch (ClassNotFoundException e){
            //driver error
            e.printStackTrace(System.err);

        }catch (SQLException e){
            //handle sql error
            e.printStackTrace(System.err);
        }finally {
            if(preparedStatement != null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace(System.err);
                }
            }
            if(con != null){
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace(System.err);
                }
            }
        }

    }


    @Override
    public void update(TripComment tripComment) {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try{
            Class.forName(Util.DRIVER);
            con = DriverManager.getConnection(Util.URL, Util.USER_ID, Util.PASSWORD);
            preparedStatement = con.prepareStatement(UPDATE_STMT);

            preparedStatement.setInt(1, tripComment.getTripId());
            preparedStatement.setInt(2, tripComment.getMemberId());
            preparedStatement.setInt(3,tripComment.getRating());
            preparedStatement.setInt(4,tripComment.getRating());
            preparedStatement.setString(5,tripComment.getComment());
            preparedStatement.setObject(6, tripComment.getStatus());
            preparedStatement.setInt(7,tripComment.getEditCount());
            preparedStatement.setObject(8,tripComment.getLastEditTime());
            preparedStatement.setInt(9, tripComment.getId());
            preparedStatement.executeUpdate();

        }catch (ClassNotFoundException e){
            e.printStackTrace(System.err);

        }catch (SQLException e){
            e.printStackTrace(System.err);

        }finally {
            if(preparedStatement != null){
                try{
                    preparedStatement.close();

                }catch (SQLException e){
                    e.printStackTrace(System.err);
                }
            }
            if(con!=null){
                try{
                    preparedStatement.close();

                }catch (SQLException e){
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    @Override
    public void delete(TripComment tripComment) {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try{
            Class.forName(Util.DRIVER);
            con = DriverManager.getConnection(Util.URL, Util.USER_ID, Util.PASSWORD);
            preparedStatement = con.prepareStatement(DELETE_STMT);

            preparedStatement.setInt(1,tripComment.getId());
            preparedStatement.executeUpdate();

        }catch (ClassNotFoundException e){
            e.printStackTrace(System.err);

        }catch (SQLException e){
            e.printStackTrace(System.err);

        }
    }

    @Override
    public TripComment findByPrimaryKey(Integer pk) {
        TripComment tripComment = null;
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            Class.forName(Util.DRIVER);
            con = DriverManager.getConnection(Util.URL, Util.USER_ID, Util.PASSWORD);
            preparedStatement = con.prepareStatement(SELECT_ONE_STMT);

            preparedStatement.setInt(1,pk);
            resultSet = preparedStatement.executeQuery();

            // get data from resultset n set to tripComment VO
            while(resultSet.next()){
                tripComment = new TripComment();
                tripComment.setId(resultSet.getInt("id"));
                tripComment.setTripId(resultSet.getInt("trip_id"));
                tripComment.setMemberId(resultSet.getInt("member_id"));
                tripComment.setRating(resultSet.getInt("rating"));
                tripComment.setComment(resultSet.getString("comment"));
                tripComment.setStatus(resultSet.getInt("status"));
                tripComment.setPostTime(resultSet.getTimestamp("post_time"));
                tripComment.setEditCount(resultSet.getInt("edit_count"));
                tripComment.setLastEditTime(resultSet.getTimestamp("last_edit_time"));

            }

        }catch (ClassNotFoundException e){
            e.printStackTrace(System.err);

        }catch (SQLException e){
            e.printStackTrace(System.err);

        }finally {
            if(preparedStatement != null){
                try{
                    preparedStatement.close();

                }catch (SQLException e){
                    e.printStackTrace(System.err);
                }
            }
            if( con != null){
                try{
                    con.close();

                }catch (SQLException e){
                    e.printStackTrace(System.err);

                }
            }
        }
        return tripComment;
    }

    @Override
    public List<TripComment> getAll() {
        List<TripComment> tripComments = new ArrayList<>();
        TripComment tripComment = null;

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            Class.forName(Util.DRIVER);
            con = DriverManager.getConnection(Util.URL, Util.USER_ID, Util.PASSWORD);
            preparedStatement = con.prepareStatement(SELECT_ALL_STMT);

            resultSet = preparedStatement.executeQuery();

            // get data from resultset n store to tripComment VO
            while (resultSet.next()){
                tripComment = new TripComment();
                tripComment.setId(resultSet.getInt("id"));
                tripComment.setTripId(resultSet.getInt("trip_id"));
                tripComment.setMemberId(resultSet.getInt("member_id"));
                tripComment.setRating(resultSet.getInt("rating"));
                tripComment.setComment(resultSet.getString("comment"));
                tripComment.setStatus(resultSet.getInt("status"));
                tripComment.setPostTime(resultSet.getTimestamp("post_time"));
                tripComment.setEditCount(resultSet.getInt("edit_count"));
                tripComment.setLastEditTime(resultSet.getTimestamp("last_edit_time"));
                tripComments.add(tripComment);
            }

        }catch (ClassNotFoundException e){
            e.printStackTrace(System.err);

        }catch (SQLException e){
            e.printStackTrace(System.err);

        }finally {
            if(preparedStatement != null){
                try{
                    preparedStatement.close();

                }catch (SQLException e){
                    e.printStackTrace(System.err);

                }
            }
            if(con != null){
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace(System.err);

                }
            }
        }

        return tripComments;
    }

    public List<TripComment> getTripComments(){

        Connection con = null;
        PreparedStatement preparedStatement = null;
        List<TripComment> tripComments = new ArrayList<>();
        ResultSet resultSet = null;
        try{
            Class.forName(Util.DRIVER);
            con = DriverManager.getConnection(Util.URL, Util.USER_ID, Util.PASSWORD);
            preparedStatement = con.prepareStatement(SELECT_FOR_TRIPCOMMENT);

            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                TripComment tripComment = new TripComment();

                tripComment.setName(resultSet.getString("name"));
                tripComment.setComment(resultSet.getString("comment"));
                tripComment.setLastEditTime(resultSet.getTimestamp("last_edit_time"));
                final byte[] buffer = resultSet.getBytes("member_pic");
                String memberPicBase64 = Base64.getEncoder().encodeToString(buffer);
                tripComment.setMemberPicBase64(memberPicBase64);

                tripComments.add(tripComment);
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();

        }catch (SQLException e){
            e.printStackTrace();

        }finally {
            if(preparedStatement != null){
                try{
                    preparedStatement.close();

                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(con!=null){
                try{
                    con.close();

                }catch (SQLException e){
                    e.printStackTrace();

                }
            }
            return tripComments;
        }
    }
}
