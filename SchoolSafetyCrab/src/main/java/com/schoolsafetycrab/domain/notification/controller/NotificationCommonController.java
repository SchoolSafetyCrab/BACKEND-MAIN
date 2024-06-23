package com.schoolsafetycrab.domain.notification.controller;

import com.schoolsafetycrab.domain.group.message.responseDto.GroupInfoResponseDto;
import com.schoolsafetycrab.domain.group.service.GroupCommonService;
import com.schoolsafetycrab.global.util.HttpResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common")
public class NotificationCommonController {

    private final NotificationCommonService groupCommonService;
    private final HttpResponseUtil responseUtil;

    @GetMapping("/find/notification")
    public ResponseEntity<?> findNotificationList(Authentication authentication) {
        List<GroupInfoResponseDto> responseDto = groupCommonService.findMyGroupList(authentication);
        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse(responseDto);

        return response;
    }

}
