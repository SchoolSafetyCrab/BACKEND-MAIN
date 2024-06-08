package com.schoolsafetycrab.domain.user.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessMessage {

    SUCCESS_SIGNUP("회원가입 성공");

    private final String message;
}
