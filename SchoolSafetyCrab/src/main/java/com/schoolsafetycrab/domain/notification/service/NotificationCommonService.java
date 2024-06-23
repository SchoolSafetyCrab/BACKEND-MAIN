package com.schoolsafetycrab.domain.notification.service;

import com.schoolsafetycrab.domain.group.message.responseDto.GroupInfoResponseDto;
import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.notification.message.responseDto.NotificationListResponseDto;
import com.schoolsafetycrab.domain.notification.model.Notification;
import com.schoolsafetycrab.domain.notification.repository.NotificationRepository;
import com.schoolsafetycrab.domain.notification.requestDto.CreateNotificationRequestDto;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.usergroup.repository.UserGroupRepository;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import com.schoolsafetycrab.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NotificationCommonService {

    private final UserGroupRepository userGroupRepository;
    private final NotificationRepository notificationRepository;

    public List<GroupInfoResponseDto> findNotificationList(Authentication authentication, long groupId) {
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        if(!userGroupRepository.existsByUser_UserIdAndGroup_GroupId(user.getUserId(), groupId))
            throw new ExceptionResponse(CustomException.ACCESS_DENIEND_EXCEPTION);

        List<Notification> notificationList = notificationRepository.findByGroup_GroupId(groupId);

        List<NotificationListResponseDto> responses = new ArrayList<>();

        for(Notification notification : notificationList) {
            responses.add();
        }

        return responses;
    }
}
