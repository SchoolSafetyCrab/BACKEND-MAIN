package com.schoolsafetycrab.domain.numberAuth.controller;

import com.schoolsafetycrab.domain.numberAuth.message.SuccessMessage;
import com.schoolsafetycrab.domain.numberAuth.requestDto.CheckAuthCodeRequestDto;
import com.schoolsafetycrab.domain.numberAuth.service.MessageService;
import com.schoolsafetycrab.domain.numberAuth.service.NumberAuthService;
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
public class MessageController {

    private final MessageService messageService;
    private final NumberAuthService numberAuthService;
    private final HttpResponseUtil httpResponseUtil;

    @PostMapping("/send/code")
    public ResponseEntity<?> saveAuthCode(@RequestBody String phoneNumber){
        messageService.sendAuthCode(phoneNumber);
        ResponseEntity<Map<String,Object>> response = httpResponseUtil.createResponse(SuccessMessage.SUCCESS_MESSAGE);
        return response;
    }

    @PostMapping("/check/code")
    public ResponseEntity<?> checkAuthCode(@RequestBody CheckAuthCodeRequestDto requestDto){
        numberAuthService.checkAuthCode(requestDto);
        ResponseEntity<Map<String,Object>> response = httpResponseUtil.createResponse(SuccessMessage.SUCCESS_CHECK_AUTH);
        return response;
    }
}
