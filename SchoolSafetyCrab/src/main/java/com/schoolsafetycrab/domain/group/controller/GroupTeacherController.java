package com.schoolsafetycrab.domain.group.controller;

import com.schoolsafetycrab.domain.group.message.SuccessGroupMessage;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.group.service.GroupService;
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
public class GroupTeacherController {

    private final GroupService groupService;
    private final HttpResponseUtil responseUtil;

    @PostMapping("/create/group")
    public ResponseEntity<?> createGroup(Authentication authentication, @Valid @RequestBody CreateGroupRequestDto createGroupRequestDto) {
        groupService.createGroup(authentication, createGroupRequestDto);
        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse(SuccessGroupMessage.SUCCESS_CREATE_GROUP);

        return response;
    }

    @GetMapping("/find/member/group")
    public ResponseEntity<?> findGroupMembers(Authentication authentication, @RequestParam("groupId") long groupId) {
        return null;
    }
}
