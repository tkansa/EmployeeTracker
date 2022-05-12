package com.tiiakansa.employeetracker.repositories;

import com.tiiakansa.employeetracker.models.Employee;
import com.tiiakansa.employeetracker.models.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
