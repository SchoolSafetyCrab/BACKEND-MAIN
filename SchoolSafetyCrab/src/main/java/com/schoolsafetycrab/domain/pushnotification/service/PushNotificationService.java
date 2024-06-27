package com.schoolsafetycrab.domain.pushnotification.service;

import com.schoolsafetycrab.domain.pushnotification.requestDto.SaveDeviceTokenRequestDto;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.pushnotification.model.PushNotification;
import com.schoolsafetycrab.domain.pushnotification.repository.PushNotificationRepository;
import com.schoolsafetycrab.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PushNotificationService {

    private final SubjectService subjectService;
    private final PushNotificationRepository pushNotificationRepository;

    @Transactional
    public void saveUserDeviceToken(Authentication authentication, SaveDeviceTokenRequestDto requestDto){
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        PushNotification pushNotification = PushNotification.createUserDevice(user, requestDto);
        if(pushNotificationRepository.existsByUser_UserId(user.getUserId()))
            pushNotificationRepository.updateDeviceToken(pushNotification);
        else
            pushNotificationRepository.save(pushNotification);
    }

    public void pushAlarm(long groupId, long notificationID){
        List<User> students = subjectService.getGroupStudent(groupId);

        subjectService.notifyUser(students, notificationID);
    }

}
