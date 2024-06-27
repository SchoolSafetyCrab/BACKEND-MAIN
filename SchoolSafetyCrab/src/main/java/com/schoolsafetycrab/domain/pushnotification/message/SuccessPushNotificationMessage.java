package com.schoolsafetycrab.domain.pushnotification.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessPushNotificationMessage {
    SUCCESS_SAVE_TOKEN("디바이스 토큰 저장 성공");

    private String message;

}
