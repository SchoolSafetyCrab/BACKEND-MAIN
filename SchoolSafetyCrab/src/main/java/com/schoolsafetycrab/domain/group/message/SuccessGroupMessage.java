package com.schoolsafetycrab.domain.group.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessGroupMessage {
    SUCCESS_CREATE_GROUP("그룹 생성 완료"),
    SUCCESS_REGIST_GROUP("그룹 등록 완료");

    private String message;

}
