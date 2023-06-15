package com.springbootReactExample.springbootbackend.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    private final String finalMessage = "User already exists!";
    public UserAlreadyExistsException(){
        super();
    }

    @Override
    public String toString() {
        return finalMessage;
    }
}

