package com.tw.employee.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tw.employee.model.Employee;
@Repository
public interface EmployeeRepository  extends JpaRepository<Employee , Integer> {
	
	 Employee findByEmployeeId(Integer Id);
	 Employee findByEmployeeName(String employeeName);
	 Employee findByEmployeeAccount(String employeeAccount);
	 
	 @Query("SELECT e.employeeAccess FROM Employee e WHERE e.employeeAccount = :employeeAccount")
	String findEmployeeAccessByEmployeeAccount(@Param("employeeAccount") String employeeAccount);
	 
	 boolean existsByEmployeeAccountAndEmployeePassword(String employeeAccount, String employeePassword);

	    default boolean hasSameAccountAndPassword(Employee employee) {
	        Employee existingEmployee = findByEmployeeAccount(employee.getEmployeeAccount());
	        return existingEmployee != null && existingEmployee.getEmployeePassword().equals(employee.getEmployeePassword());
	    }
	 
	 

}
