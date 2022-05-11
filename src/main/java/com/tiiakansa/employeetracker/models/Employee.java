package com.tiiakansa.employeetracker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document("employees")
public class Employee {

    @Id
    public String id;

    private String firstName;

    private String lastName;

    private Address address;

    private String companyEmail;

    private String birthDate;

    private String hiredDate;

    private Role role;

    private List<Skill> skills;

    public Employee(){}

    public Employee(String firstName, String lastName, Address address, String companyEmail, String birthDate, String hiredDate, Role role, List<Skill> skills) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.companyEmail = companyEmail;
        this.birthDate = birthDate;
        this.hiredDate = hiredDate;
        this.role = role;
        this.skills = skills;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(String hiredDate) {
        this.hiredDate = hiredDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
