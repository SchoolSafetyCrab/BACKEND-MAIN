package com.schoolsafetycrab.domain.PushNotification.service;

import com.schoolsafetycrab.domain.pushnotification.model.PushNotification;
import com.schoolsafetycrab.domain.pushnotification.repository.PushNotificationRepository;
import com.schoolsafetycrab.domain.pushnotification.requestDto.SaveDeviceTokenRequestDto;
import com.schoolsafetycrab.domain.pushnotification.service.PushNotificationService;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.global.security.auth.PrincipalDetails;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PushNotificationServiceTest {

    @InjectMocks
    private PushNotificationService pushNotificationService;

    @Mock
    private PushNotificationRepository pushNotificationRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private PrincipalDetails principalDetails;

    private SaveDeviceTokenRequestDto requestDto;
    private User user;
    private PushNotification pushNotification;

    @BeforeEach
    public void init() {
        user = User.createUser("test", "test", "test", "test", Role.ROLE_STUDENT, "010-1234-5678");
        requestDto = new SaveDeviceTokenRequestDto("asdfaweasdfA");
        pushNotification = PushNotification.createUserDevice(user, requestDto);
    }

    @Test
    @DisplayName("디바이스 토큰 저장 성공 테스트")
    public void 디바이스_토큰_저장_성공_테스트(){
        //given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(user);
        BDDMockito.given(pushNotificationRepository.existsByUser_UserId(user.getUserId()))
                .willReturn(false);
        BDDMockito.given(pushNotificationRepository.save(any(PushNotification.class))).willReturn(pushNotification);

        //when
        Assertions.assertThatNoException().isThrownBy(()-> pushNotificationService.saveUserDeviceToken(authentication, requestDto));

        BDDMockito.then(pushNotificationRepository).should().existsByUser_UserId(user.getUserId());
        BDDMockito.then(pushNotificationRepository).should().save(any(PushNotification.class));

    }

    @Test
    @DisplayName("디바이스 토큰 업데이트 성공 테스트")
    public void 디바이스_토큰_업데이트_성공_테스트(){
        //given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(user);
        BDDMockito.given(pushNotificationRepository.existsByUser_UserId(user.getUserId()))
                .willReturn(true);
        BDDMockito.given(pushNotificationRepository.updateDeviceToken(any(PushNotification.class))).willReturn(1L);

        //when
        Assertions.assertThatNoException().isThrownBy(()-> pushNotificationService.saveUserDeviceToken(authentication, requestDto));

        BDDMockito.then(pushNotificationRepository).should().existsByUser_UserId(user.getUserId());
        BDDMockito.then(pushNotificationRepository).should().updateDeviceToken(any(PushNotification.class));

    }

}
