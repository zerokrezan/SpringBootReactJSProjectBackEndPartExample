package com.springbootReactExample.springbootbackend.exceptions;

public class UserDoesNotExistException extends RuntimeException{

    private final String finalMessage = "User does not exist!";
    public UserDoesNotExistException(){
        super();
    }

    @Override
    public String toString() {
        return finalMessage;
    }
}
