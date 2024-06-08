package com.schoolsafetycrab.domain.message.service;

import com.schoolsafetycrab.domain.numberAuth.repository.NumberAuthRepository;
import com.schoolsafetycrab.domain.numberAuth.requestDto.CheckAuthCodeRequestDto;
import com.schoolsafetycrab.domain.numberAuth.service.NumberAuthService;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MessageServiceCheckTest {

    @InjectMocks
    private NumberAuthService numberAuthService;

    @Mock
    private NumberAuthRepository numberAuthRepository;

    public CheckAuthCodeRequestDto requestDto;

    @BeforeEach
    public void setUp(){
        requestDto = new CheckAuthCodeRequestDto("01011111111","12345678");
    }

    @Test
    @DisplayName("인증번호 검증 테스트")
    public void 인증번호_검증_테스트(){
        //given
        BDDMockito.given(numberAuthRepository.checkAuth(requestDto.getPhoneNumber(), requestDto.getAuthCode())).willReturn(true);

        //when
        Assertions.assertThatNoException().isThrownBy(() -> numberAuthService.checkAuthCode(requestDto));

        //then
        BDDMockito.then(numberAuthRepository).should().checkAuth(requestDto.getPhoneNumber(), requestDto.getAuthCode());
    }

    @Test
    @DisplayName("인증번호 검증 실패 테스트")
    public void 인증번호_검증_실패_테스트(){
        //given
        BDDMockito.given(numberAuthRepository.checkAuth(requestDto.getPhoneNumber(), requestDto.getAuthCode())).willReturn(false);

        Assertions.assertThatThrownBy(() -> numberAuthService.checkAuthCode(requestDto))
                .isInstanceOf(ExceptionResponse.class)
                .hasFieldOrPropertyWithValue("customException",CustomException.NOT_MATCH_AUTH_CODE);

        BDDMockito.then(numberAuthRepository).should().checkAuth(requestDto.getPhoneNumber(), requestDto.getAuthCode());
    }
}
