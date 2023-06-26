package com.tw.trip.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tw.trip.pojo.Trip;
import com.tw.trip.service.TripSearchService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

@RestController
@RequestMapping("/tripSearch")
public class TripSearchController {

	@Autowired
	TripSearchService tripSearchService;

	@GetMapping("/getTripList")
	public String getTripList() {
		final List<Trip> tripList = tripSearchService.getTripListWithPic();
		final String json = new Gson().toJson(tripList);

		return json;
	}

	@PostMapping("/getTripsBySearch2")
	public int test(@RequestBody final Dto dto) {
		for (final String s : dto.getCities()) {
			System.out.println(s);
		}
		return 0;
	}

	@Data
	private static class Dto {
		private String[] cities;
	}

	@PostMapping("/getTripsBySearch")
	public String getTripsBySearch(final HttpServletRequest request) throws IOException {
		final BufferedReader reader = request.getReader();
		final StringBuilder stringBuilder = new StringBuilder();
		String dataRead;
		String json = null;

		// ====== 1. retrieved data from request n store in stringBuilder ======
		while ((dataRead = reader.readLine()) != null) {
			stringBuilder.append(dataRead);
		}

		reader.close();

		// ====== 2. parse to JSON via JSONObject ======
		JSONObject jsonObject;

		try {
			jsonObject = new JSONObject(stringBuilder.toString());  // arguments for JSONObject is String
			// ====== 1. deal with TripOrder ======

			// ====== 2. deal with String[] cities ======
			final String cities = jsonObject.getString("cities");
			System.out.println("test 2" + cities);

			final ObjectMapper objectMapper = new ObjectMapper();
			final String[] cityList = objectMapper.readValue(cities, String[].class);

			final List<Trip> tripList = tripSearchService.getTripBySearch(cityList);
			for (final Trip trip : tripList) {
				System.out.println(trip.getCity());
			}

			json = new Gson().toJson(tripList);

		} catch (final JSONException e) {
			e.printStackTrace();

		}

		return json;
	}

}
