package com.schoolsafetycrab.domain.group.controller;

import com.schoolsafetycrab.domain.group.message.SuccessGroupMessage;
import com.schoolsafetycrab.domain.group.service.GroupService;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
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
@RequestMapping("/api")
public class GroupController {

    private final GroupService groupService;
    private final HttpResponseUtil responseUtil;

    @PostMapping("/teacher/group/create")
    public ResponseEntity<?> createGroup(Authentication authentication, @RequestBody CreateGroupRequestDto requestDto) {
        groupService.createGroup(authentication, requestDto);
        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse(SuccessGroupMessage.SUCCESS_CREATE_GROUP);

        return response;
    }
}
