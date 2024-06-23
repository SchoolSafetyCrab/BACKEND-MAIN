package com.schoolsafetycrab.domain.notification.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessNotificationMessage {
    SUCCESS_CREATE_NOTIFICATION("공지 생성 완료");

    private String message;

}
