package com.schoolsafetycrab.domain.pushalarm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.Message;
import com.schoolsafetycrab.domain.notification.model.Notification;
import com.schoolsafetycrab.domain.notification.repository.NotificationRepository;
import com.schoolsafetycrab.domain.pushalarm.message.responseDto.PushAlarmResponseDto;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.userdevice.model.UserDevice;
import com.schoolsafetycrab.domain.userdevice.repository.UserDeviceRepository;
import com.schoolsafetycrab.domain.usergroup.repository.UserGroupRepository;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PushAlarmService {

    private final SubjectService subjectService;

    public void pushAlarm(long groupId, long notificationID){
        List<User> students = subjectService.getGroupStudent(groupId);

        subjectService.notifyUser(students, notificationID);
    }

}
