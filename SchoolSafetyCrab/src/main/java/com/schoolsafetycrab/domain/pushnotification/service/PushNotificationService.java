package com.schoolsafetycrab.domain.pushnotification.service;

import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.pushnotification.model.PushNotification;
import com.schoolsafetycrab.domain.pushnotification.repository.PushNotificationRepository;
import lombok.RequiredArgsConstructor;
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
    public void saveUserDeviceToken(User user, String deviceToken){
        PushNotification pushNotification = PushNotification.createUserDevice(user, deviceToken);
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
