package com.gmail.robertzylan.atiperaTask.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String reason) {
        super(reason);
    }
}
