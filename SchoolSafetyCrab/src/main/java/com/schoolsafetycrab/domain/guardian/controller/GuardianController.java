package com.schoolsafetycrab.domain.guardian.controller;

import com.schoolsafetycrab.domain.guardian.service.GuardianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parents")
public class GuardianController {

    private final GuardianService guardianService;

    @GetMapping
    public ResponseEntity<?> myChildren(Authentication authentication){

    }

}
