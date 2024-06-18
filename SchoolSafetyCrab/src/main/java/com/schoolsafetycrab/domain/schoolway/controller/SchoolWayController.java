package com.schoolsafetycrab.domain.schoolway.controller;

import com.schoolsafetycrab.domain.schoolway.message.SuccessSchoolWayMessage;
import com.schoolsafetycrab.domain.schoolway.requestDto.SchoolWayPointRequestDto;
import com.schoolsafetycrab.domain.schoolway.service.SchoolWayService;
import com.schoolsafetycrab.global.util.HttpResponseUtil;
import jakarta.validation.Valid;
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
public class SchoolWayController {

    private final SchoolWayService schoolWayService;
    private final HttpResponseUtil responseUtil;

    @PostMapping("/save/schoolway")
    public ResponseEntity<?> saveSchoolWay(Authentication authentication, @Valid @RequestBody SchoolWayPointRequestDto requestDto) {
        schoolWayService.saveSchoolWay(authentication, requestDto);
        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse(SuccessSchoolWayMessage.SUCCESS_SCHOOL_WAY);
        return response;
    }

}
