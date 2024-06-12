package com.schoolsafetycrab.domain.user.controller;

import com.schoolsafetycrab.domain.user.message.SuccessStudentMessage;
import com.schoolsafetycrab.domain.user.requestDto.DesignateGuardianRequestDto;
import com.schoolsafetycrab.domain.user.service.StudentService;
import com.schoolsafetycrab.global.util.HttpResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {

    private StudentService studentService;
    private HttpResponseUtil responseUtil;

    @PostMapping("/designate/guardian")
    public ResponseEntity<?> designateGuardian(Authentication authentication, @RequestBody DesignateGuardianRequestDto requestDto){
        studentService.designateGuardian(authentication,requestDto);
        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse(SuccessStudentMessage.SUCCESS_DESIGNATE_GUARDIAN);
        return response;
    }
}
