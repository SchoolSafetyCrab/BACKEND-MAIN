package com.schoolsafetycrab.domain.group.controller;

import com.schoolsafetycrab.domain.group.message.SuccessGroupMessage;
import com.schoolsafetycrab.domain.group.message.responseDto.GroupInfoResponseDto;
import com.schoolsafetycrab.domain.group.message.responseDto.GroupMemberResponseDto;
import com.schoolsafetycrab.domain.group.requestDto.RegistGroupRequestDto;
import com.schoolsafetycrab.domain.group.service.GroupService;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
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
@RequestMapping("/api/student")
public class GroupStudentController {

    private final GroupService groupService;
    private final HttpResponseUtil responseUtil;

    @PostMapping("/regist/group")
    public ResponseEntity<?> registGroup(Authentication authentication, @Valid @RequestBody RegistGroupRequestDto registGroupRequestDto) {
        groupService.registGroup(authentication, registGroupRequestDto);
        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse(SuccessGroupMessage.SUCCESS_REGIST_GROUP);

        return response;
    }

    @GetMapping("/search/group")
    public ResponseEntity<?> searchGroup(@RequestParam("keyword") String keyword) {
        List<GroupInfoResponseDto> responseDto = groupService.searchGroup(keyword);
        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse(responseDto);

        return response;
    }
}
