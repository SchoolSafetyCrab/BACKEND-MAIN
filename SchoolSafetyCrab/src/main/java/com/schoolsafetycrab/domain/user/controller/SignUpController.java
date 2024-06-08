package com.schoolsafetycrab.domain.user.controller;

import com.schoolsafetycrab.domain.user.message.SuccessMessage;
import com.schoolsafetycrab.domain.user.requestDto.SignUpRequestDto;
import com.schoolsafetycrab.domain.user.service.UserService;
import com.schoolsafetycrab.global.util.HttpResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/join")
public class SignUpController {

    private final UserService userService;
    private final HttpResponseUtil responseUtil;

    @PostMapping
    public ResponseEntity<?> signupUser(@RequestBody SignUpRequestDto requestDto){
        userService.saveUser(requestDto);
        ResponseEntity<Map<String,Object>> response = responseUtil.createResponse(SuccessMessage.SUCCESS_SIGNUP);
        return response;
    }
}
