package com.praxiti.etl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praxiti.etl.model.Employee;
import com.praxiti.etl.repo.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository empRepo;

	public void saveAllEmployees(List<Employee> employees) {
		empRepo.saveAll(employees);
	}

}
