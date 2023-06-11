package com.tw.trip.controller;

import com.tw.trip.service.TripCommentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/tripCommentGet")
public class GetTripCommentController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String dataRead;

        // ====== 1. retrieved data from request n store in stringBuilder ======
        while((dataRead=reader.readLine()) != null){
            stringBuilder.append(dataRead);
        }

        reader.close();

        // ====== 2. parse to JSON via JSONObject ======
        JSONObject jsonObject;
        String param = null;

        try{
            jsonObject = new JSONObject(stringBuilder.toString());  // arguments for JSONObject is String
            param = jsonObject.getString("message");

        }catch (JSONException e){
            e.printStackTrace();

        }

        // ====== 3. Sent data to DB ======
        TripCommentService tripCommentService = new TripCommentService();
        tripCommentService.addTripComment(2, 3, 1,
                param, 2, 3);

        System.out.println("update successfully!");

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Response from Servlet");
    }


}
