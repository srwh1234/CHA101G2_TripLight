package com.tw.trip.controller;


import com.google.gson.Gson;
import com.tw.trip.model.dao.TripDao;
import com.tw.trip.model.pojo.Trip;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/tripGet")
public class TripController extends HttpServlet {

    @Autowired
    TripDao tripDao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer tripId = Integer.valueOf(request.getParameter("tripId"));
        Trip trip = tripDao.findByPrimaryKey(tripId);


        String json = new Gson().toJson(trip);

        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(json);
    }
}
