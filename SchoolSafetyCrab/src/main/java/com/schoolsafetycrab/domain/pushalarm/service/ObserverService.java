package com.schoolsafetycrab.domain.pushalarm.service;

import com.schoolsafetycrab.domain.notification.model.Notification;
import com.schoolsafetycrab.domain.pushalarm.dao.fcm.FCMDao;
import com.schoolsafetycrab.domain.pushalarm.message.responseDto.PushAlarmResponseDto;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.userdevice.model.UserDevice;
import com.schoolsafetycrab.domain.userdevice.repository.UserDeviceRepository;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObserverService {

    private final UserDeviceRepository userDeviceRepository;;
    private final FCMDao fcmDao;

    public void notifyUser(User user, Notification notification){
        UserDevice userDevice = userDeviceRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new ExceptionResponse(CustomException.NOT_FOUND_DEVICETOKEN_EXCEPTION));

        PushAlarmResponseDto pushAlarmResponseDto = PushAlarmResponseDto.createPushAlarmResponseDto(userDevice, notification);

        fcmDao.pushMessage(pushAlarmResponseDto);
    }

}
