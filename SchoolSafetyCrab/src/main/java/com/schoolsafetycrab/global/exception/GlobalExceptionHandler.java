package com.schoolsafetycrab.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorResponse.class)
    public ResponseEntity<?> handlerException(ErrorResponse e){
        return new ResponseEntity<>(e, HttpStatus.valueOf(e.getCustomException().getStatusNum()));
    }
}
