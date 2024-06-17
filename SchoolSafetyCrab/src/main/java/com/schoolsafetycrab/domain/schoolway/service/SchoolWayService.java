package com.schoolsafetycrab.domain.schoolway.service;

import com.schoolsafetycrab.domain.schoolway.model.SchoolWay;
import com.schoolsafetycrab.domain.schoolway.repository.SchoolWayRepository;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import com.schoolsafetycrab.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SchoolWayService {

    private final SchoolWayRepository schoolWayRepository;

    @Transactional
    public SchoolWay saveSchoolWay(Authentication authentication) {
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        if(!user.getRole().equals(Role.ROLE_STUDENT)){
            throw new ExceptionResponse(CustomException.ACCESS_DENIEND_EXCEPTION);
        }

        SchoolWay schoolWay = SchoolWay.createSchoolWay()

    }
}
