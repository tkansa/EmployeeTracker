package com.tiiakansa.employeetracker.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Skill {

    @Id
    private String id;
    private Field field;
    private Integer experience;

    public Skill(){};

    public Skill(Field field, Integer experience) {
        this.id = new ObjectId().toString();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
