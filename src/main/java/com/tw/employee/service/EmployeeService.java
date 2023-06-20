package com.tw.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.employee.dao.EmployeeRepository;
import com.tw.employee.model.Employee;

@Service
public class EmployeeService{

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    public boolean isValidAccount(String account, String password) {
        Employee employee = employeeRepository.findByEmployeeAccount(account);
        return employee != null && employee.getEmployeePassword().equals(password);
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }
    
    public Employee updateEmployee(Employee employee) {
        // 執行資料庫更新操作
    	return  employeeRepository.save(employee);
    }

//    
//    public List<Employee> getRealtimeEmployeeData() {
//        return employeeRepository.findAll();
//    }
//    
//    public List<Employee> getLatestEmployees() {
//        return employeeRepository.findAll();
//    }
}