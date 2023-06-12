package com.tw.employee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Setter
@ToString
public class EmployeeLogin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeid;

    private String employeename;
    private String employeetel;
    private String employeeAccount;
    private String employeePassword;
    private int employeeidStatus;

  
}
