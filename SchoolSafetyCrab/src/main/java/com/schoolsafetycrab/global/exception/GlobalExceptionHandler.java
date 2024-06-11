package com.schoolsafetycrab.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExceptionResponse.class)
    public ResponseEntity<?> handlerException(ExceptionResponse e){
        return new ResponseEntity<>(e, HttpStatus.valueOf(e.getCustomException().getStatusNum()));
    }
}
