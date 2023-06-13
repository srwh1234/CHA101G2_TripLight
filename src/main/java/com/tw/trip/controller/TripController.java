package com.tw.trip.controller;


import com.google.gson.Gson;
import com.tw.trip.model.dao.TripDaoImpl;
import com.tw.trip.model.pojo.Trip;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/tripGet")
public class TripController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer tripId = Integer.valueOf(request.getParameter("tripId"));
        TripDaoImpl tripDao = new TripDaoImpl();
        Trip trip = tripDao.findByPrimaryKey(tripId);

        String json = new Gson().toJson(trip);

        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(json);
    }
}
