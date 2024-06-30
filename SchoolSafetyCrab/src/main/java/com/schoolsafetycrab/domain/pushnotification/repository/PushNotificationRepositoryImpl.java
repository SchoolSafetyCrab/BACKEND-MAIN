package com.schoolsafetycrab.domain.pushnotification.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.schoolsafetycrab.domain.pushnotification.model.PushNotification;
import com.schoolsafetycrab.domain.pushnotification.model.QPushNotification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PushNotificationRepositoryImpl implements PushNotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private QPushNotification qPushNotification = QPushNotification.pushNotification;

    @Override
    public long updateDeviceToken(PushNotification pushNotification) {
        return queryFactory.update(qPushNotification)
                .set(qPushNotification.deviceToken, pushNotification.getDeviceToken())
                .where(qPushNotification.user.eq(pushNotification.getUser()))
                .execute();
    }

}
