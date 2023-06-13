package com.tw.employee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tw.employee.dao.EmployeeRepository;
import com.tw.employee.model.Employee;

@Service
public class EmployeeService{
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }
    
    public List<Employee> getRealtimeEmployeeData() {
        return employeeRepository.findAll();
    }
}