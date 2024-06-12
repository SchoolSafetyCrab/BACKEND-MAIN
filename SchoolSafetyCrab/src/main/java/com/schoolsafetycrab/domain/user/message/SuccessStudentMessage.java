package com.schoolsafetycrab.domain.user.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessStudentMessage {

    SUCCESS_DESIGNATE_GUARDIAN("보호자 지정 성공")
    ;

    private final String message;
}
