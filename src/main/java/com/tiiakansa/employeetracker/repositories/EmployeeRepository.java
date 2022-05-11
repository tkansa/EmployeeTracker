package com.tiiakansa.employeetracker.repositories;

import com.tiiakansa.employeetracker.models.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

}
