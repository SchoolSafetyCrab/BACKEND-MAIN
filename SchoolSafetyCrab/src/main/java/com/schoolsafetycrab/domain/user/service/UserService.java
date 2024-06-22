package com.schoolsafetycrab.domain.user.service;

import com.schoolsafetycrab.domain.user.message.responseDto.UserInfoResponseDto;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.user.repository.UserRepository;
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
public class UserService {

    public UserInfoResponseDto findUserInfo(Authentication authentication){
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        UserInfoResponseDto responseDto = UserInfoResponseDto.createUserInfoResponseDto(user);
        return responseDto;
    }
}
