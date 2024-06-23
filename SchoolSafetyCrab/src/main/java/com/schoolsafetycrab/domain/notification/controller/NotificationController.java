package com.schoolsafetycrab.domain.notification.controller;

import com.schoolsafetycrab.domain.group.message.SuccessGroupMessage;
import com.schoolsafetycrab.domain.notification.message.SuccessNotificationMessage;
import com.schoolsafetycrab.domain.notification.requestDto.CreateNotificationRequestDto;
import com.schoolsafetycrab.domain.notification.service.NotificationService;
import com.schoolsafetycrab.global.util.HttpResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher")
public class NotificationController {

    private final NotificationService notificationService;
    private final HttpResponseUtil responseUtil;

    @PostMapping("/create/notification")
    public ResponseEntity<?> createNotification(Authentication authentication, @Valid @RequestBody CreateNotificationRequestDto requestDto) {
        notificationService.createNotification(authentication, requestDto);

        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse(SuccessNotificationMessage.SUCCESS_CREATE_NOTIFICATION);

        return response;
    }
}
