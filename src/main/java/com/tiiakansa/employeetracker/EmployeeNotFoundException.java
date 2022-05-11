package com.tiiakansa.employeetracker;

import com.tiiakansa.employeetracker.models.Employee;

public class EmployeeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException(String id){
        super("Could not find employee with ID: " + id);
    }
}
