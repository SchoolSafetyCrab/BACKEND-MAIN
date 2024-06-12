package com.schoolsafetycrab.domain.user.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessSignUpMessage {

    SUCCESS_SIGNUP("회원가입 성공"),
    SUCCESS_CHECK_ID("아이디 중복 체크 성공")
    ;

    private final String message;
}
