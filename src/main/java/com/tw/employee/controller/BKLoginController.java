package com.tw.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.employee.dao.EmployeeRepository;
import com.tw.employee.model.Employee;
import com.tw.employee.service.BKLoginService;

import jakarta.servlet.http.HttpSession;

@RestController
public class BKLoginController {
	
	private final EmployeeRepository employeeRepository;
	private final BKLoginService bkLoginService;
	
	@Autowired
	public BKLoginController(EmployeeRepository employeeRepository, BKLoginService bkLoginService) {
		this.employeeRepository = employeeRepository;
		this.bkLoginService = bkLoginService;
	}
	
	@PostMapping("/BKlogin")
	public Boolean BKlogin(@RequestParam String account, @RequestParam String password, HttpSession session) {
		Employee result = bkLoginService.bklogin(account, password);
		
		if(result == null) {
			System.out.println("查無此員工");	
			return false;
		} else {
			session.setAttribute("employee", result);
			System.out.println("EmployeeId: " + session.getAttribute("employee"));	
			return true;
		}
	}	
				
}
	
	
	
	

	
	
	
	
	
	
	


