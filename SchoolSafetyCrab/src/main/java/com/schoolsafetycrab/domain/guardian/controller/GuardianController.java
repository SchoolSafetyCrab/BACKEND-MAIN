package com.schoolsafetycrab.domain.guardian.controller;

import com.schoolsafetycrab.domain.guardian.message.responseDto.MyChildrenResponseDto;
import com.schoolsafetycrab.domain.guardian.service.GuardianService;
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
@RequestMapping("/api/parents")
public class GuardianController {

    private final GuardianService guardianService;
    private final HttpResponseUtil responseUtil;

    @GetMapping
    public ResponseEntity<?> myChildren(Authentication authentication){
        List<MyChildrenResponseDto> responses = guardianService.myChildren(authentication);
        ResponseEntity<Map<String,Object>> response = responseUtil.createResponse(responses);
        return response;
    }
}
