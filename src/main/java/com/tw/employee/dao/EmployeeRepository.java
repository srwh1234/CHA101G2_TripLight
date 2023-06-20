package com.tw.employee.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tw.employee.model.Access;
import com.tw.employee.model.Employee;
@Repository
public interface EmployeeRepository  extends JpaRepository<Employee , Integer> {
	
	 Employee findByEmployeeId(Integer Id);
	 Employee findByEmployeeName(String employeeName);
	 Employee findByEmployeeAccount(String employeeAccount);
	 

}
