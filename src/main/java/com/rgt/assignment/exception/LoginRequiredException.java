package com.rgt.assignment.exception;

public class LoginRequiredException extends UserException{

    private static final String MESSAGE = "로그인이 필요한 서비스입니다.";

    public LoginRequiredException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
