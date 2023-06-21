package com.tw.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.employee.dao.EmployeeRepository;
import com.tw.employee.model.Employee;

@Service
public class BKLoginService {
	private final EmployeeRepository employeeRepository;

	@Autowired
	public BKLoginService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public Employee bklogin(String account, String password) {
		Employee employee = employeeRepository.findByEmployeeAccount(account);

		if (employee != null && employee.getEmployeePassword().equals(password)) {
			// 帳號和密碼驗證成功
			return employee;
		}

		// 帳號和密碼驗證失敗
		return null;
	}
}
