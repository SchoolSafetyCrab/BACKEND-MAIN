package com.schoolsafetycrab.domain.user.controller;

import com.schoolsafetycrab.domain.user.message.SuccessMessage;
import com.schoolsafetycrab.domain.user.requestDto.CheckIdRequestDto;
import com.schoolsafetycrab.domain.user.requestDto.SignUpRequestDto;
import com.schoolsafetycrab.domain.user.service.SignUpService;
import com.schoolsafetycrab.global.util.HttpResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/join")
public class SignUpController {

    private final SignUpService signUpService;
    private final HttpResponseUtil responseUtil;

    @PostMapping
    public ResponseEntity<?> signupUser(@RequestBody SignUpRequestDto requestDto){
        signUpService.saveUser(requestDto);
        ResponseEntity<Map<String,Object>> response = responseUtil.createResponse(SuccessMessage.SUCCESS_SIGNUP);
        return response;
    }

    @PostMapping("/check/id")
    public ResponseEntity<?> checkId(@RequestBody CheckIdRequestDto requestDto){
        signUpService.checkId(requestDto);
        ResponseEntity<Map<String,Object>> response = responseUtil.createResponse(SuccessMessage.SUCCESS_CHECK_ID);
        return response;
    }
}
