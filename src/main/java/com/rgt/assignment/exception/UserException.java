package com.rgt.assignment.exception;

public abstract class UserException extends RuntimeException{

    public UserException(String message) {
        super(message);
    }

    public abstract int getStatusCode();
}
