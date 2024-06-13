package com.schoolsafetycrab.domain.guardian.service;

import com.schoolsafetycrab.domain.guardian.repository.GuardianRepository;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GuardianService {

    private final GuardianRepository guardianRepository;

    public List<User> myChildren(Authentication authentication){
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();


    }
}
