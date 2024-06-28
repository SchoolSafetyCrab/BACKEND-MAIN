package com.schoolsafetycrab.domain.pushnotification.dao.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.schoolsafetycrab.domain.pushnotification.message.responseDto.PushNotificationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class FCMDao {

    public void pushMessage(PushNotificationResponseDto pushNotificationResponseDto){

        Message message = Message.builder()
                .putData("title", pushNotificationResponseDto.getTitle())
                .putData("content", pushNotificationResponseDto.getDetail())
                .setToken(pushNotificationResponseDto.getDeviceToken())
                .build();

        FirebaseMessaging.getInstance().sendAsync(message);
    }
}
