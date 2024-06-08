package com.schoolsafetycrab.domain.numberAuth.service;

import com.schoolsafetycrab.domain.numberAuth.repository.MessageRepository;
import com.schoolsafetycrab.domain.numberAuth.requestDto.CheckAuthCodeRequestDto;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class NumberAuthService {

    private final MessageRepository messageRepository;

    public String saveAuthCode(String phoneNumber){
        String authCode = createAuthCode();
        log.info("authCode : {} ", authCode);

        messageRepository.saveAuth(phoneNumber,authCode);
        return authCode;
    }

    public void checkAuthCode(CheckAuthCodeRequestDto requestDto){
        String phoneNumber = requestDto.getPhoneNumber();
        String authCode = requestDto.getAuthCode();

        if(!messageRepository.checkAuth(phoneNumber,authCode)){
            throw new ExceptionResponse(CustomException.NOT_MATCH_AUTH_CODE);
        }
        messageRepository.saveSuccessNumber(phoneNumber);
    }

    private String createAuthCode(){
        Random random = new Random();
        StringBuffer code = new StringBuffer();

        for(int i = 0; i < 8; i++){
            int index = random.nextInt(3);
            switch(index){
                case 0:
                    code.append((char) (random.nextInt(26) + 97));
                    break;
                case 1:
                    code.append((char)(random.nextInt(26) + 65));
                    break;
                case 2:
                    code.append((char)(random.nextInt(26) + 48));
                    break;
            }
        }
        return code.toString();
    }
}
