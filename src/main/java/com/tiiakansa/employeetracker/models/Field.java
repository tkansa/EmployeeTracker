package com.tiiakansa.employeetracker.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Field {
    @Id
    private String id;
    private String name;
    private String type;



    public Field(){}
    public Field(String name, String type){
        this.id = new ObjectId().toString();
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
