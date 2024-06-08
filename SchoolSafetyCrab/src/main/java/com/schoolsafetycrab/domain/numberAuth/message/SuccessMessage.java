package com.schoolsafetycrab.domain.numberAuth.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessMessage {

    SUCCESS_MESSAGE("인증번호 보내기 성공");

    private final String message;
}
