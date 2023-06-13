package com.tw.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.employee.dao.AccessRepository;
import com.tw.employee.model.Access;

@RestController // 標示該類別與前端交互，自動轉Json
public class AccessController {
	
	@Autowired
	private AccessRepository accessRepository;
	
	@GetMapping("/Access")
	public List<Access> getAccess(){
		return accessRepository.findAll();
		
	}

}
