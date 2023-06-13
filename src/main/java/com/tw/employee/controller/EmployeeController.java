package com.tw.employee.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.employee.model.Employee;
import com.tw.employee.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// 新增
	@PostMapping
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		Employee createdEmployee = employeeService.saveEmployee(employee);
		return ResponseEntity.ok(createdEmployee);
	}

	// 根據ID查
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id) {
		Employee employee = employeeService.getEmployeeById(id);
		if (employee != null) {
			return ResponseEntity.ok(employee);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	// 獲得全部員工
	@GetMapping("/employees/employee")
	public ResponseEntity<List<Employee>> getRealtimeEmployeeData() {
	  List<Employee> employees = employeeService.getAllEmployees();
	  return ResponseEntity.ok(employees);
	}
	
	// 獲得全部員工
	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = employeeService.getAllEmployees();
		return ResponseEntity.ok(employees);
	}

}
