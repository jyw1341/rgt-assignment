package com.rgt.assignment.exception;

public class LoginFailException extends UserException{

    private static final String MESSAGE = "이메일 또는 비밀번호가 올바르지 않습니다.";

    public LoginFailException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
