package com.schoolsafetycrab.domain.pushnotification.controller;

import com.schoolsafetycrab.domain.pushnotification.message.responseDto.SuccessPushNotificationMessage;
import com.schoolsafetycrab.domain.pushnotification.requestDto.SaveDeviceTokenRequestDto;
import com.schoolsafetycrab.domain.pushnotification.service.PushNotificationService;
import com.schoolsafetycrab.global.util.HttpResponseUtil;
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
@RequestMapping("/api/student")
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;
    private final HttpResponseUtil responseUtil;

    @PostMapping("/save/deviceToken")
    public ResponseEntity<?> saveToken(Authentication authentication, @RequestBody SaveDeviceTokenRequestDto requestDto) {
        pushNotificationService.saveUserDeviceToken(authentication, requestDto);

        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse(SuccessPushNotificationMessage.SUCCESS_SAVE_TOKEN);

        return response;
    }
}
