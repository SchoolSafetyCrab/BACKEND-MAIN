package com.schoolsafetycrab.domain.pushalarm.dao.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.schoolsafetycrab.domain.notification.repository.NotificationRepository;
import com.schoolsafetycrab.domain.pushalarm.message.responseDto.PushAlarmResponseDto;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.userdevice.model.UserDevice;
import com.schoolsafetycrab.domain.userdevice.repository.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FCMDao {

    private final UserDeviceRepository userDeviceRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void saveUserDeviceToken(User user, String deviceToken){
        UserDevice userDevice = UserDevice.createUserDevice(user, deviceToken);
        if(userDeviceRepository.existsByUser_UserId(user.getUserId()))
            userDeviceRepository.updateDeviceToken(userDevice);
        else
            userDeviceRepository.save(userDevice);
    }

    public void pushMessage(PushAlarmResponseDto pushAlarmResponseDto){

        Message message = Message.builder()
                .putData("title", pushAlarmResponseDto.getTitle())
                .putData("content", pushAlarmResponseDto.getDetail())
                .setToken(pushAlarmResponseDto.getDeviceToken())
                .build();

        FirebaseMessaging.getInstance().sendAsync(message);
    }
}
