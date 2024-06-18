package com.schoolsafetycrab.domain.schoolway.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessSchoolWayMessage {

    SUCCESS_SCHOOL_WAY("등하굣길 등록 성공");

    private final String message;
}
