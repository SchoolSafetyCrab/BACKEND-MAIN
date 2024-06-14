package com.schoolsafetycrab.domain.declaration.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessMessage {

    SUCCESS_REQUEST_DECLARATION("신고 요청 성공")
    ;

    private final String message;
}
