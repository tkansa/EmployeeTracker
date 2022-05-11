package com.tiiakansa.employeetracker.models;

public class Skill {
    private Field field;
    private Integer experience;

    public Skill(){};

    public Skill(Field field, Integer experience) {
        this.field = field;
        this.experience = experience;
    }

    public Field getField() {
        return field;
    }
    public void setField(Field field) {
        this.field = field;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}
