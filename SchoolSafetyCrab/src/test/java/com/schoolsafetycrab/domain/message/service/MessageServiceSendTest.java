package com.schoolsafetycrab.domain.message.service;

import com.schoolsafetycrab.domain.numberAuth.requestDto.SendAuthCodeRequestDto;
import com.schoolsafetycrab.domain.numberAuth.service.MessageService;
import com.schoolsafetycrab.domain.numberAuth.service.NumberAuthService;
import com.schoolsafetycrab.domain.user.repository.UserRepository;
import com.schoolsafetycrab.global.config.MessageValueConfig;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MessageServiceSendTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageValueConfig messageValueConfig;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NumberAuthService numberAuthService;

    @Mock
    private DefaultMessageService defaultMessageService;


    public SendAuthCodeRequestDto requestDto;

    @BeforeEach
    public void setUp(){
        requestDto = new SendAuthCodeRequestDto("010-1111-1111");
    }

    @Test
    @DisplayName("인증번호 보내기 테스트")
    public void 인증_번호_보내기(){
        //given
        String authCode = "123456";

        BDDMockito.given(userRepository.existsUserByPhoneNumber(requestDto.getPhoneNumber())).willReturn(false);
        BDDMockito.given(numberAuthService.saveAuthCode(requestDto.getPhoneNumber())).willReturn(authCode);
        BDDMockito.given(messageValueConfig.getDefaultMessageService()).willReturn(defaultMessageService);

        //when
        Assertions.assertThatNoException().isThrownBy(() -> messageService.sendAuthCode(requestDto));

        //then
        BDDMockito.then(userRepository).should().existsUserByPhoneNumber(requestDto.getPhoneNumber());
        BDDMockito.then(numberAuthService).should().saveAuthCode(requestDto.getPhoneNumber());
        BDDMockito.then(defaultMessageService).should().sendOne(any(SingleMessageSendingRequest.class));
    }

    @Test
    @DisplayName("이미 회원인 전화번호 예외 테스트")
    public void 전화번호_예외_테스트(){
        //given
        BDDMockito.given(userRepository.existsUserByPhoneNumber(requestDto.getPhoneNumber())).willReturn(true);

        //when
        Assertions.assertThatThrownBy(() ->  messageService.sendAuthCode(requestDto))
                .isInstanceOf(ExceptionResponse.class)
                .hasFieldOrPropertyWithValue("customException", CustomException.DUPLICATED_NUMBER_EXCEPTION);

        // then
        BDDMockito.then(userRepository).should().existsUserByPhoneNumber(requestDto.getPhoneNumber());
        BDDMockito.then(numberAuthService).shouldHaveNoInteractions();
        BDDMockito.then(defaultMessageService).shouldHaveNoInteractions();
    }
}
