package com.schoolsafetycrab.domain.group.controller;

import com.schoolsafetycrab.domain.group.message.responseDto.GroupInfoResponseDto;
import com.schoolsafetycrab.domain.group.service.GroupService;
import com.schoolsafetycrab.global.util.HttpResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GroupCommonController {

    private final GroupService groupService;
    private final HttpResponseUtil responseUtil;

    @GetMapping("/find/group")
    public ResponseEntity<?> findGroupList(Authentication authentication) {
        List<GroupInfoResponseDto> responseDto = groupService.findGroupList(authentication);
        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse(responseDto);

        return response;
    }
}
