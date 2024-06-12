package com.schoolsafetycrab.domain.user.controller;

import com.schoolsafetycrab.domain.user.requestDto.DesignateGuardianRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {

    @PostMapping("/designate/guardian")
    public ResponseEntity<?> designateGuardian(Authentication authentication, @RequestBody DesignateGuardianRequestDto requestDto){


    }
}
