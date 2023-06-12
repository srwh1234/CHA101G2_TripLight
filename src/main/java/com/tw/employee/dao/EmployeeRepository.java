package com.tw.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tw.employee.model.Access;
import com.tw.employee.model.Employee;

public interface EmployeeRepository  extends JpaRepository<Employee , Integer> {

}
