package com.schoolsafetycrab.domain.declaration.controller;

import com.schoolsafetycrab.domain.declaration.message.SuccessMessage;
import com.schoolsafetycrab.domain.declaration.requestDto.DeclarationRequestDto;
import com.schoolsafetycrab.domain.declaration.service.DeclarationService;
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
@RequestMapping("/api/declaration")
public class DeclarationController {

    private final DeclarationService declarationService;
    private final HttpResponseUtil responseUtil;

    @PostMapping
    public ResponseEntity<?> requestDeclaration(Authentication authentication, @Valid @RequestBody DeclarationRequestDto requestDto){
        declarationService.requestDeclaration(authentication,requestDto);
        ResponseEntity<Map<String,Object>> response = responseUtil.createResponse(SuccessMessage.SUCCESS_REQUEST_DECLARATION);
        return response;
    }
}
