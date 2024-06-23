package com.schoolsafetycrab.domain.notification.service;

import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.notification.model.Notification;
import com.schoolsafetycrab.domain.notification.repository.NotificationRepository;
import com.schoolsafetycrab.domain.notification.requestDto.CreateNotificationRequestDto;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.usergroup.model.UserGroup;
import com.schoolsafetycrab.domain.usergroup.repository.UserGroupRepository;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final UserGroupRepository userGroupRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void createNotification(Authentication authentication, CreateNotificationRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();

        Group group = userGroupRepository.findByUser_UserIdAndGroup_GroupId(user.getUserId(), requestDto.getGroupId())
                .map(UserGroup::getGroup)
                .orElseThrow(() -> new ExceptionResponse(CustomException.ACCESS_DENIEND_EXCEPTION));

        notificationRepository.save(Notification.createNotification(group, requestDto));
    }
}
