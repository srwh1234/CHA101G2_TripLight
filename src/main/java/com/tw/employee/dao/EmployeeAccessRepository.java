package com.tw.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.employee.model.Access;
import com.tw.employee.model.EmployeeAccess;
@Repository
public interface EmployeeAccessRepository extends JpaRepository<EmployeeAccess , Integer> {
	
	
	
	
	

}
