package com.tiiakansa.employeetracker.controllers;

import com.tiiakansa.employeetracker.EmployeeNotFoundException;
import com.tiiakansa.employeetracker.InvalidDataException;
import com.tiiakansa.employeetracker.SkillNotFoundException;
import com.tiiakansa.employeetracker.models.*;
import com.tiiakansa.employeetracker.repositories.EmployeeRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@CrossOrigin
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
        Employee emp = new Employee("Tiia", "Kansa", new Address("1535 Merrick", "Detroit", "MI", "48208", "US"), "tiiakansa313@gmail.com", "09/20/2010", "05/20/2022", Role.TECHNICAL_CONSULTANT, skills);
        repo.insert(emp);

        skills = Arrays.asList(
                new Skill(new Field("Aerodynamics", "Physics"), 35),
                new Skill(new Field("Calculus", "Mathmatics"), 35)
        );
        emp = new Employee("Mary", "Jackson", new Address("1535 Woodward", "Hampton", "VA", "23661", "US"), "mjackson@nasa.com", "09/20/2000", "05/20/2022", Role.CHIEF, skills);
        repo.insert(emp);

        skills = Arrays.asList(
                new Skill(new Field("Cryptoanalytics", "Code Breaking"), 35),
                new Skill(new Field("Numismatics", "Code Breaking"), 35)
        );
        emp = new Employee("Elizabeth", "Clarke", new Address("Bletchley Park, Sherwood Dr.", "Milton Keynes", "Buckinghamshire", "MK3 6EB", "US"), "mjackson@nasa.com", "09/20/2000", "05/20/2022", Role.CHIEF, skills);
        repo.insert(emp);

        return "Data reset.";
    }

    // EMPLOYEE

    // Get all Perficient employees
    @CrossOrigin
    @GetMapping("/employees")
    public List<Employee> readAll(){
        return repo.findAll();
    }

    // Find a Perficient employee by ID
    @GetMapping("/employees/{id}")
    public Employee readOne(@PathVariable("id") String id){
        return repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    // Create a Perficient employee
    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee){
        // need to manually add in IDs for the nested objects
        // is there a better way to accomplish this?
        try{
            Address address = employee.getAddress();
            address.setId(new ObjectId().toString());
            for (Skill skill : employee.getSkills()) {
                skill.setId(new ObjectId().toString());
                Field field = skill.getField();
                field.setId(new ObjectId().toString());
            }
            repo.insert(employee);
        } catch (Exception e){
            throw new InvalidDataException("Invalid Perficient employee data sent to server.");
        }
        return employee;
    }

    // Update a Perficient Employee by ID
    @PutMapping("/employees/{id}")
    public Employee update(@PathVariable("id") String id, @RequestBody Employee employee){

        // The built-in Spring Data MongoRepository method doesn't require an ID
        // But I left it in the endpoint to conform with specs
        Optional<Employee> emp = repo.findById(id);
        if(!emp.isPresent()){
            throw new EmployeeNotFoundException(id);
        }
        return repo.save(employee);
    }

    // Delete a Perficient employee by ID
    @DeleteMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id){

        Optional<Employee> employee = repo.findById(id);
        if(!employee.isPresent()){
            throw new EmployeeNotFoundException(id);
        }
        repo.deleteById(id);
    }

    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(EmployeeNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String invalidDataHandler(InvalidDataException ex){
        return ex.getMessage();
    }

    // SKILL

    // Get all technical skills for a Perficient employee
    // TODO there is probably a better way to write a query in the repo
    // that returns the skills, rather than the entire employee object
    @GetMapping("/employees/{id}/skills")
    public List<Skill> readAll(@PathVariable("id") String id){
        Employee employee = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return employee.getSkills();
    }

    // Find a technical skill by ID for a Perficient employee by ID
    @GetMapping("/employees/{id}/skills/{skillsId}")
    public Skill readOne(@PathVariable("id") String id, @PathVariable("skillsId") String skillId){
        Employee employee = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        Skill skill = null;
        for(Skill s: employee.getSkills()){
            String i = s.getId();
            if(i.equals(skillId)){
                skill = s;
            }
        }
        if(skill == null){
            throw new SkillNotFoundException(skillId);
        }
        return skill;
    }

    // Add a technical skill to a Perficient employee by ID
    @PostMapping("/employees/{id}/skills")
    @ResponseStatus(HttpStatus.CREATED)
    public Skill create(@PathVariable String id, @RequestBody Skill skill){
        Employee employee = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        Skill s = new Skill();
        s.setField(skill.getField());
        s.getField().setId(new ObjectId().toString());
        s.setExperience(skill.getExperience());
        s.setId(new ObjectId().toString());
        employee.getSkills().add(s);
        repo.save(employee);
        return s;
    }

    // Update a technical skill by ID for a Perficient employee by ID
    @PutMapping("employees/{id}/skills/{skillId}")
    public Skill Update(@PathVariable("id") String id, @PathVariable("skillId") String skillId, @RequestBody Skill skill){
        Employee employee = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        Skill skl = null;
        for(Skill s: employee.getSkills()){
            if(s.getId().equals(skillId)){
                s.setId(skillId);
                s.setField(skill.getField());
                s.setExperience(skill.getExperience());
                skl = s;
            }
        }
        if(skill == null){
            throw new SkillNotFoundException(skillId);
        }
        repo.save(employee);
        return skl;
    }

    // Delete a technical skill for a Perficient employee by ID
    @DeleteMapping("employees/{id}/skills/{skillId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id, @PathVariable("skillId") String skillId){
        Employee employee = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        boolean hasSkill = false;
        for(int i = 0; i < employee.getSkills().size(); i++){
            if(employee.getSkills().get(i).getId().equals(skillId)){
                employee.getSkills().remove(employee.getSkills().get(i));
                hasSkill = true;
            }
        }
        if(!hasSkill){
            throw new SkillNotFoundException(skillId);
        }
        repo.save(employee);
    }

    @ResponseBody
    @ExceptionHandler(SkillNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String skillNotFoundHandler(SkillNotFoundException ex){
        return ex.getMessage();
    }
}
