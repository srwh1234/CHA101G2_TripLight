package com.tw.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.model.dao.MyFavoriteRepository;

@Service
public class MyFavoriteService {
	@Autowired
	private MyFavoriteRepository mtFavoriteRepository;
	
}
