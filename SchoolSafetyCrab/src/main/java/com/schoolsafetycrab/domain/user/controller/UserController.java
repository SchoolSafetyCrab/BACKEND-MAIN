package com.schoolsafetycrab.domain.user.controller;

import com.schoolsafetycrab.domain.user.message.responseDto.UserInfoResponseDto;
import com.schoolsafetycrab.domain.user.service.UserService;
import com.schoolsafetycrab.global.util.HttpResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final HttpResponseUtil responseUtil;

    @GetMapping
    public ResponseEntity<?> findUserInfo(Authentication authentication){
        UserInfoResponseDto responseDto = userService.findUserInfo(authentication);
        ResponseEntity<Map<String, Object>> response = responseUtil.createResponse( responseDto);
        return response;
    }
}
