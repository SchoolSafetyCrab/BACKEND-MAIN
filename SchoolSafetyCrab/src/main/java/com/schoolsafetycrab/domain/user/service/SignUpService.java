package com.schoolsafetycrab.domain.user.service;

import com.schoolsafetycrab.domain.numberAuth.repository.NumberAuthRepository;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.user.repository.UserRepository;
import com.schoolsafetycrab.domain.user.requestDto.CheckIdRequestDto;
import com.schoolsafetycrab.domain.user.requestDto.SignUpRequestDto;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SignUpService {

    private final UserRepository userRepository;
    private final NumberAuthRepository numberAuthRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void saveUser(SignUpRequestDto requestDto){

        if(!numberAuthRepository.existsBuSuccessNumber(requestDto.getPhoneNumber())){
            throw new ExceptionResponse(CustomException.NOT_AUTH_NUMBER_EXCEPTION);
        }

        if(userRepository.existsUserById(requestDto.getId())){
            throw new ExceptionResponse(CustomException.DUPLICATED_ID_EXCEPTION);
        }
        if(userRepository.existsUserByPhoneNumber(requestDto.getPhoneNumber())){
            throw new ExceptionResponse(CustomException. DUPLICATED_NUMBER_EXCEPTION);
        }

        User user = User.createUser(requestDto.getId(), passwordEncoder.encode(requestDto.getPassword()), requestDto.getNickname(), requestDto.getIconImg(), requestDto.getRole(),requestDto.getPhoneNumber());
        userRepository.save(user);
    }

    public boolean checkId(CheckIdRequestDto requestDto){
        if(userRepository.existsUserById(requestDto.getId())){
            throw new ExceptionResponse(CustomException.DUPLICATED_ID_EXCEPTION);
        }
        return true;
    }
}
