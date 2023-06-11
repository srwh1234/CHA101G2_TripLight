package com.tw.trip.controller;

import com.google.gson.Gson;
import com.tw.trip.model.dao.TripCommentDaoImpl;
import com.tw.trip.model.pojo.TripComment;
import com.tw.trip.service.TripCommentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "*")
@WebServlet("/tripCommentSent")
public class SentTripCommentController extends HttpServlet {

//    public static void main(String[] args){
//        TripCommentService dao = new TripCommentService();
//        List<TripComment> tripComments = dao.getTripComments();
//
//        System.out.println(tripComments.get(0).getMemberPicBase64());
//    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TripCommentService tripCommentService = new TripCommentService();
        List<TripComment> tripCommentList = tripCommentService.getTripComments();

        Gson gson = new Gson();
        String json = gson.toJson(tripCommentList);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(json);

    }

}
