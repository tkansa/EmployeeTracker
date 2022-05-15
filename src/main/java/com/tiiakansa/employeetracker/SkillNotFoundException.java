package com.tiiakansa.employeetracker;

public class SkillNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SkillNotFoundException(String id){
        super("Could not find skill with ID: " + id);
    }
}
