package com.schoolsafetycrab.domain.group.controller;

import com.schoolsafetycrab.domain.group.message.SuccessGroupMessage;
import com.schoolsafetycrab.domain.group.message.responseDto.GroupMemberResponseDto;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.group.service.GroupCommonService;
import com.schoolsafetycrab.domain.group.service.GroupTeacherService;
import com.schoolsafetycrab.global.util.HttpResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher")
public class GroupTeacherController {

    private final GroupTeacherService groupTeacherService;
    private final HttpResponseUtil responseUtil;

    @PostMapping("/create/group")
    public ResponseEntity<?> createGroup(Authentication authentication, @Valid @RequestBody CreateGroupRequestDto createGroupRequestDto) {
        groupTeacherService.createGroup(authentication, createGroupRequestDto);
        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse(SuccessGroupMessage.SUCCESS_CREATE_GROUP);

        return response;
    }

    @GetMapping("/member/group/{groupId}")
    public ResponseEntity<?> findGroupMembers(Authentication authentication, @PathVariable long groupId) {
        List<GroupMemberResponseDto> responseDto = groupTeacherService.findGroupMembers(authentication, groupId);
        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse(responseDto);

        return response;
    }
}
