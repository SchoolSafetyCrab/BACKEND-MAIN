package com.schoolsafetycrab.domain.numberAuth.controller;

import com.schoolsafetycrab.domain.numberAuth.message.SuccessMessage;
import com.schoolsafetycrab.domain.numberAuth.service.MessageService;
import com.schoolsafetycrab.global.util.HttpResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/join")
public class MessageController {

    private final MessageService messageService;
    private final HttpResponseUtil httpResponseUtil;

    @PostMapping("/save/code")
    public ResponseEntity<?> saveAuthCode(@RequestBody String phoneNumber){
        messageService.sendAuthCode(phoneNumber);
        return httpResponseUtil.createResponse(SuccessMessage.SUCCESS_MESSAGE);
    }
}