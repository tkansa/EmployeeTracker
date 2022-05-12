package com.tiiakansa.employeetracker.controllers;

import com.tiiakansa.employeetracker.EmployeeNotFoundException;
import com.tiiakansa.employeetracker.models.*;
import com.tiiakansa.employeetracker.repositories.EmployeeRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class EmployeeController {

    // uses DI to make an instance of the repo available
    @Autowired
    private EmployeeRepository repo;

    // reseed the db
    @GetMapping("/reset")
    public String reset(){

        // clear out the database
        repo.deleteAll();

        // add in some data
        List<Skill> skills = Arrays.asList(
                new Skill(new Field("Java", "Software Development"), 2),
                new Skill(new Field("Googling", "Internet"), 20)
        );
        Employee emp = new Employee("Tiia", "Kansa", new Address("1535 Merrick", "Detroit", "MI", "48208", "US"), "tiiakansa313@gmail.com", "09/20/1973", "05/20/2022", Role.TECHNICAL_CONSULTANT, skills);
        repo.insert(emp);

        skills = Arrays.asList(
                new Skill(new Field("DJing", "Entertainment"), 5),
                new Skill(new Field("Lecturing", "Education"), 12)
        );
        emp = new Employee("Susan", "Smith", new Address("1535 Woodward", "Detroit", "MI", "48210", "US"), "suzy@gmail.com", "09/20/2000", "05/20/2022", Role.CHIEF, skills);
        repo.insert(emp);

        return "Data reset.";
    }

    // Get all Perficient employees
    @GetMapping("/employee")
    public List<Employee> readAll(){
        return repo.findAll();
    }

    // Find a Perficient employee by ID
    @GetMapping("/employee/{id}")
    public Employee readOne(@PathVariable("id") String id){
        return repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    // Create a Perficient employee
    @PostMapping("/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee){
        // need to manually add in IDs for the skills
        // TODO is there a better way to accomplish this?
        for (Skill skill: employee.getSkills()) {
            skill.setId(new ObjectId().toString());
        }
        repo.insert(employee);
        return employee;
    }

    // Update a Perficient Employee by ID

    // Delete a Perficient employee by ID
    @DeleteMapping("/employee/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id){
        // TODO what if ID doesn't exist - test this
        repo.deleteById(id);
    }

    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(EmployeeNotFoundException ex){
        return ex.getMessage();
    }
}
