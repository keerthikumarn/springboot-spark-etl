package com.praxiti.etl.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "employees")
public class Employee {

	@Id
	private String employeeID;
	private String firstName;
	private String lastName;
	private int Salary;
	private String department;
	private LocalDate hiringDate;
	private int salaryWithBonus;
	private String departmentUpperCase;
	private double experience;

}
