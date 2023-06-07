package com.tw.trip.controller;

import com.google.gson.Gson;
import com.tw.trip.model.pojo.TripComment;
import com.tw.trip.service.TripCommentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.List;

@WebServlet("/tripCommentController")
public class TripCommentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TripCommentService tripCommentService = new TripCommentService();
        List<TripComment> tripCommentList = tripCommentService.getAll();

        Gson gson = new Gson();
        String json = gson.toJson(tripCommentList);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.getWriter().write(json);

    }
}
