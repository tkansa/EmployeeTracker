package com.tiiakansa.employeetracker;

import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

public class InvalidDataException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public InvalidDataException(String message){
        super(message);
    }
}
