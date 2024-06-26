package com.schoolsafetycrab.domain.pushalarm.message.responseDto;

import com.schoolsafetycrab.domain.notification.model.Notification;
import com.schoolsafetycrab.domain.userdevice.model.UserDevice;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PushAlarmResponseDto {

    private String deviceToken;

    private String title;

    private String detail;

    public PushAlarmResponseDto(String deviceToken, String title, String detail){
        this.deviceToken = deviceToken;
        this.title = title;
        this.detail = detail;
    }

    public static PushAlarmResponseDto createPushAlarmResponseDto(UserDevice userDevice, Notification notification){
        return new PushAlarmResponseDto(userDevice.getDeviceToken(), notification.getTitle(), notification.getDetail());
    }
}
