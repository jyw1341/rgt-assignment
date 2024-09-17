package com.rgt.assignment.exception;

public class InvalidRequestException extends UserException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequestException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
