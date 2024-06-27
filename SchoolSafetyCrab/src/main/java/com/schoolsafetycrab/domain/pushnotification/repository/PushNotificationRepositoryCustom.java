package com.schoolsafetycrab.domain.pushnotification.repository;

import com.schoolsafetycrab.domain.pushnotification.model.PushNotification;

public interface PushNotificationRepositoryCustom {
    long updateDeviceToken(PushNotification pushNotification);
}
