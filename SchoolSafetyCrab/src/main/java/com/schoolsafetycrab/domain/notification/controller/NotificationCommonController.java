package com.schoolsafetycrab.domain.notification.controller;

import com.schoolsafetycrab.domain.group.message.responseDto.GroupInfoResponseDto;
import com.schoolsafetycrab.domain.group.service.GroupCommonService;
import com.schoolsafetycrab.domain.notification.message.responseDto.NotificationListResponseDto;
import com.schoolsafetycrab.domain.notification.service.NotificationCommonService;
import com.schoolsafetycrab.global.util.HttpResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common")
public class NotificationCommonController {

    private final NotificationCommonService notificationCommonService;
    private final HttpResponseUtil responseUtil;

    @GetMapping("/find/notification/{groupId}")
    public ResponseEntity<?> findNotificationList(Authentication authentication, @PathVariable("groupId") long groupId) {
        List<NotificationListResponseDto> responseDto = notificationCommonService.findNotificationList(authentication, groupId);
        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse(responseDto);

        return response;
    }

}
