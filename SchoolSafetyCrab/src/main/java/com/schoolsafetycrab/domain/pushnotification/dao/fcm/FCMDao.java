package com.schoolsafetycrab.domain.pushnotification.dao.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.schoolsafetycrab.domain.notification.repository.NotificationRepository;
import com.schoolsafetycrab.domain.pushnotification.message.responseDto.PushNotificationResponseDto;
import com.schoolsafetycrab.domain.pushnotification.repository.PushNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FCMDao {

    private final PushNotificationRepository pushNotificationRepository;
    private final NotificationRepository notificationRepository;

    public void pushMessage(PushNotificationResponseDto pushNotificationResponseDto){

        Message message = Message.builder()
                .putData("title", pushNotificationResponseDto.getTitle())
                .putData("content", pushNotificationResponseDto.getDetail())
                .setToken(pushNotificationResponseDto.getDeviceToken())
                .build();

        FirebaseMessaging.getInstance().sendAsync(message);
    }
}
