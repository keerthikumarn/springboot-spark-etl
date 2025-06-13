package com.praxiti.etl.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.praxiti.etl.model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee,String> {

}
