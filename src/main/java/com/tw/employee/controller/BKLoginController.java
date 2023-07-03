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
	public Employee BKlogin(@RequestParam String account, @RequestParam String password, HttpSession session) {
		Employee result = bkLoginService.bklogin(account, password);

		// 先檢查帳號密碼是否為員工表格內有的
		if (result == null) {
			//System.out.println("查無此員工");
			return null;
			// 在檢查員工狀態要是在職、不是就不行登
		} else if (result.getEmployeeStatus() == 0) {
			//System.out.println("員工狀態不符合");
			return null;
			// 都通過就可以成功登入
		} else {
			String employeeAccess = bkLoginService
			.findEmployeeAccessByEmployeeAccount(result.getEmployeeAccount());
			session.setAttribute("employee", result);
			session.setAttribute("employeeAccess", employeeAccess);
			//System.out.println("EmployeeId: " + session.getAttribute("employee"));
			return result;

		}
	}
}
