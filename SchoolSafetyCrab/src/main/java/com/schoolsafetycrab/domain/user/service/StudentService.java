package com.schoolsafetycrab.domain.user.service;

import com.schoolsafetycrab.domain.guardian.model.Guardian;
import com.schoolsafetycrab.domain.guardian.repository.GuardianRepository;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.user.repository.UserRepository;
import com.schoolsafetycrab.domain.user.requestDto.DesignateGuardianRequestDto;
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
public class StudentService {

    private final UserRepository userRepository;
    private final GuardianRepository guardianRepository;

    @Transactional
    public void designateGuardian(Authentication authentication, DesignateGuardianRequestDto requestDto){
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();
        User guardian = userRepository.findUserByIdAndState(requestDto.getGuardianId(),true).orElseThrow(() -> {
            throw new ExceptionResponse(CustomException.NOT_FOUND_USER_EXCEPTION);
        });

        if(guardian.getRole().equals(Role.ROLE_STUDENT)){
            throw new ExceptionResponse(CustomException.DENIEND_STUDENT_EXCEPTION);
        }

        if(guardianRepository.existsGuardianByUserAndId(user,guardian.getId())){
            throw new ExceptionResponse(CustomException.DUPLICATED_GUARDIAN_ID_EXCEPTION);
        }

        Guardian designateGuardian = Guardian.createGuardian(user,guardian.getId());
        guardianRepository.save(designateGuardian);
    }
}
