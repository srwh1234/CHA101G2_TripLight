package com.tw.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.member.service.RatingService;


@RestController
public class RatingController {
	@Autowired
	private RatingService ratingService;
	
	//票券訂單數
	  @GetMapping("/rating/{memberId}")
	    public int rate(@PathVariable("memberId") Integer memberId) {
	        return ratingService.sum(memberId);
	    }
	 //旅行團訂單數
	  @GetMapping("/rating2/{memberId}")
	  public int rate2(@PathVariable("memberId") Integer memberId) {
		  return ratingService.sum2(memberId);
	  }
	  
	//會員分級存回資料庫
	  @PostMapping("/updateRating")
	  public void updateRating(@RequestParam int id, @RequestParam int memberGrade) {
		   ratingService.upgradeRating(id, memberGrade);
	  }
	
}
