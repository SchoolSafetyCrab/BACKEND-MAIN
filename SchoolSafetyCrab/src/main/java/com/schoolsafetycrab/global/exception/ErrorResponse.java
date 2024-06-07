package com.schoolsafetycrab.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse extends RuntimeException {

    private CustomException customException;
}
