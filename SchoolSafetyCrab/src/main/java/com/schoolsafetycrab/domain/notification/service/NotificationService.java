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
import com.schoolsafetycrab.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NotificationService {

    private final UserGroupRepository userGroupRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void createNotification(Authentication authentication, CreateNotificationRequestDto requestDto) {
        User user = ((PrincipalDetails)authentication.getPrincipal()).getUser();

        Group group = userGroupRepository.findByUser_UserIdAndGroup_GroupId(user.getUserId(), requestDto.getGroupId())
                .orElseThrow(() -> new ExceptionResponse(CustomException.ACCESS_DENIEND_EXCEPTION)).getGroup();

        notificationRepository.save(Notification.createNotification(group, requestDto));
    }

    @Transactional
    public void deleteNotification(Authentication authentication, Long notificationId) {
        User user = ((PrincipalDetails)authentication.getPrincipal()).getUser();

        long groupId = notificationRepository.findByNotificationId(notificationId)
                .orElseThrow(() -> new ExceptionResponse(CustomException.NOT_FOUND_NOTIFICATION_EXCEPTION)).getGroup().getGroupId();

        if(!userGroupRepository.existsByUser_UserIdAndGroup_GroupId(user.getUserId(), groupId))
            throw new ExceptionResponse(CustomException.ACCESS_DENIEND_EXCEPTION);

        notificationRepository.deleteById(notificationId);
    }
}
