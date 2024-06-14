package com.schoolsafetycrab.domain.declaration.controller;

import com.schoolsafetycrab.domain.declaration.requestDto.DeclarationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/declaration")
public class DeclarationController {

    @PostMapping
    public ResponseEntity<?> requestDeclaration(Authentication authentication, @RequestBody DeclarationRequestDto requestDto){

    }

}
