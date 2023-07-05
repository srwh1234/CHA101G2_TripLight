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

    public void createEmployee(Employee employee) {
        if (employeeRepository.existsByEmployeeAccountAndEmployeePassword(employee.getEmployeeAccount(), employee.getEmployeePassword())) {
            throw new IllegalArgumentException("相同的帳號和密碼已存在");
        }

        // 執行新增操作
        employeeRepository.save(employee);
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