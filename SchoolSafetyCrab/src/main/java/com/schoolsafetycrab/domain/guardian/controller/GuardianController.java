package com.schoolsafetycrab.domain.guardian.controller;

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

    @GetMapping
    public ResponseEntity<?> myChildren(Authentication authentication){

    }

}
