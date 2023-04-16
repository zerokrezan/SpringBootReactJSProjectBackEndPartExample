package com.springbootReactExample.springbootbackend.exceptions;

public class PasswordIsInUseException extends RuntimeException{
    private final String finalMessage = "Password is already in use!";
    public PasswordIsInUseException(){
        super();
    }

    @Override
    public String toString() {
        return finalMessage;
    }
}
