package com.schoolsafetycrab.domain.guardian.controller;

import com.schoolsafetycrab.domain.guardian.message.responseDto.MyChildrenResponseDto;
import com.schoolsafetycrab.domain.guardian.service.GuardianService;
import com.schoolsafetycrab.domain.schoolway.message.responseDto.PointResponseDto;
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

    @GetMapping("/schoolway")
    public ResponseEntity<?> myChildrenSchoolWay(Authentication authentication, @RequestParam("userId") long userId){
        List<PointResponseDto> responses = guardianService.findMyChildrenSchoolWay(authentication, userId);
        ResponseEntity<Map<String,Object>> response = responseUtil.createResponse(responses);
        return response;
    }
}
