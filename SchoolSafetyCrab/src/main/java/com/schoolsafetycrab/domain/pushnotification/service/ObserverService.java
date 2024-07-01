package com.schoolsafetycrab.domain.pushnotification.service;

import com.schoolsafetycrab.domain.notification.model.Notification;
import com.schoolsafetycrab.domain.pushnotification.dao.fcm.FCMDao;
import com.schoolsafetycrab.domain.pushnotification.message.responseDto.PushNotificationResponseDto;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.pushnotification.model.PushNotification;
import com.schoolsafetycrab.domain.pushnotification.repository.PushNotificationRepository;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObserverService {

    private final PushNotificationRepository pushNotificationRepository;;
    private final FCMDao fcmDao;

    public void notifyUser(User user, Notification notification){

        if(!pushNotificationRepository.existsByUser_UserId(user.getUserId())){
            return;
        }

        PushNotification pushNotification = pushNotificationRepository.findByUser_UserId(user.getUserId());

        PushNotificationResponseDto pushNotificationResponseDto = PushNotificationResponseDto.createPushNotificationResponseDto(pushNotification, notification);

        fcmDao.pushMessage(pushNotificationResponseDto);
    }

}
