package com.schoolsafetycrab.domain.pushalarm.service;

import com.schoolsafetycrab.domain.notification.model.Notification;
import com.schoolsafetycrab.domain.notification.repository.NotificationRepository;
import com.schoolsafetycrab.domain.user.model.User;
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
public class SubjectService {

    private final UserGroupRepository userGroupRepository;
    private final NotificationRepository notificationRepository;
    private final ObserverService observerService;

    public List<User> getGroupStudent(long groupId){
        return userGroupRepository.findGroupMemberByGroupId(groupId);
    }

    public void notifyUser(List<User> students, long notificationId){
        Notification notification = notificationRepository.findByNotificationId(notificationId)
                .orElseThrow(() -> new ExceptionResponse(CustomException.NOT_FOUND_NOTIFICATION_EXCEPTION));

        for(User student:students){
            observerService.notifyUser(student, notification);
        }
    }

}
