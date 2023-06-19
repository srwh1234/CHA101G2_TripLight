package com.tw.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tw.member.service.MyFavoriteService;

@Controller
public class MyFavoriteController {
	@Autowired
	private MyFavoriteService myFavoriteService;
}
