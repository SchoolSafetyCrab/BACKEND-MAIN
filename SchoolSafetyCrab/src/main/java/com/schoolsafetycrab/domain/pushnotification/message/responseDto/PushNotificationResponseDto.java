package com.schoolsafetycrab.domain.pushnotification.message.responseDto;

import com.schoolsafetycrab.domain.notification.model.Notification;
import com.schoolsafetycrab.domain.pushnotification.model.PushNotification;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PushNotificationResponseDto {

    private String deviceToken;

    private String title;

    private String detail;

    public PushNotificationResponseDto(String deviceToken, String title, String detail){
        this.deviceToken = deviceToken;
        this.title = title;
        this.detail = detail;
    }

    public static PushNotificationResponseDto createPushNotificationResponseDto(PushNotification pushNotification, Notification notification){
        return new PushNotificationResponseDto(pushNotification.getDeviceToken(), notification.getTitle(), notification.getDetail());
    }
}
