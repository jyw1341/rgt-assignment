package com.rgt.assignment.controller;

import com.rgt.assignment.dto.ErrorResponse;
import com.rgt.assignment.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> userExHandle(UserException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getStatusCode(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(e.getStatusCode()));
    }
}
