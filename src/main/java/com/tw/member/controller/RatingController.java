package com.tw.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.member.service.RatingService;


@RestController
public class RatingController {
	@Autowired
	private RatingService ratingServiceImpl;
	
//	  @GetMapping("/rating/{memberId}")
//	    public int rate(@PathVariable("memberId") Integer memberId) {
//	        return ratingServiceImpl.sum(memberId);
//	    }
	  @GetMapping("/rating")
	  public int rate() {
		  return ratingServiceImpl.sum(1);
	  }
	
}
