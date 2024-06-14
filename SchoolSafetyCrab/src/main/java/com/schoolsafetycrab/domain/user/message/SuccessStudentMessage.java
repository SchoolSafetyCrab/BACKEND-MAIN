package com.schoolsafetycrab.domain.user.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessStudentMessage {

    SUCCESS_DESIGNATE_GUARDIAN("보호자 지정 성공")
    ;

    private final String message;
}
