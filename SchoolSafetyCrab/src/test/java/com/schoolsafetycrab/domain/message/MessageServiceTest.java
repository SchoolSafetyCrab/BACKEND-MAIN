package com.schoolsafetycrab.domain.message;

import com.schoolsafetycrab.domain.numberAuth.service.MessageService;
import com.schoolsafetycrab.domain.numberAuth.service.NumberAuthService;
import com.schoolsafetycrab.domain.user.repository.UserRepository;
import com.schoolsafetycrab.global.config.MessageValueConfig;
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
public class MessageServiceTest {

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

    @BeforeEach
    void setUp() {
        BDDMockito.given(messageValueConfig.getDefaultMessageService()).willReturn(defaultMessageService);
    }

    @Test
    @DisplayName("인증번호 보내기 테스트")
    public void 인증_번호_보내기(){
        //given
        String phoneNumber = "01029463517";
        String authCode = "123456";
        String messageText = "인증 번호는123456입니다";

        BDDMockito.given(userRepository.existsUserByPhoneNumber(phoneNumber)).willReturn(false);
        BDDMockito.given(numberAuthService.saveAuthCode(phoneNumber)).willReturn(authCode);

        //when
        Assertions.assertThatNoException().isThrownBy(() -> messageService.sendAuthCode(phoneNumber));

        //then
        BDDMockito.then(userRepository).should().existsUserByPhoneNumber(phoneNumber);
        BDDMockito.then(numberAuthService).should().saveAuthCode(phoneNumber);
        BDDMockito.then(defaultMessageService).should().sendOne(any(SingleMessageSendingRequest.class));
    }




}
