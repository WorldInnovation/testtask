package com.spintech.testtask.error;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException (String fullName){
        super("Person fullName not found :" + fullName);
    }
}
