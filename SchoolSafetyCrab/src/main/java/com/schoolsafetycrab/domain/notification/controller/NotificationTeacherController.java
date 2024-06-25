package com.schoolsafetycrab.domain.notification.controller;

import com.schoolsafetycrab.domain.notification.message.SuccessNotificationMessage;
import com.schoolsafetycrab.domain.notification.requestDto.CreateNotificationRequestDto;
import com.schoolsafetycrab.domain.notification.service.NotificationTeacherService;
import com.schoolsafetycrab.global.util.HttpResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher")
public class NotificationTeacherController {

    private final NotificationTeacherService notificationTeacherService;
    private final HttpResponseUtil responseUtil;

    @PostMapping("/create/notification")
    public ResponseEntity<?> createNotification(Authentication authentication, @Valid @RequestBody CreateNotificationRequestDto requestDto) {
        notificationTeacherService.createNotification(authentication, requestDto);

        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse(SuccessNotificationMessage.SUCCESS_CREATE_NOTIFICATION);

        return response;
    }

    @DeleteMapping("/delete/notification/{notificationId}")
    public ResponseEntity<?> deleteNotification(Authentication authentication, @PathVariable("notificationId") long notificationId) {
        notificationTeacherService.deleteNotification(authentication, notificationId);
        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse(SuccessNotificationMessage.SUCCESS_DELETE_NOTIFICATION);

        return response;
    }
}
