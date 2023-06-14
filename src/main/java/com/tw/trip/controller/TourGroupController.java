package com.tw.trip.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tw.trip.model.dao.TourGroupDao;
import com.tw.trip.model.dao.TourGroupDaoImpl;
import com.tw.trip.model.pojo.TourGroup;
import com.tw.trip.service.TourGroupService;
import jakarta.servlet.annotation.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tourGroupGet")
public class TourGroupController extends HttpServlet {

    @Autowired
    TourGroupService tourGroupService;

    @Autowired
    TourGroupDao tourGroupDao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer tripId = Integer.valueOf(request.getParameter("tripId"));
        TourGroup tourGroup = tourGroupService.getTourGroupWithDates(tripId,2,tourGroupDao);

        String json = new Gson().toJson(tourGroup);

        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
