package com.schoolsafetycrab.domain.PushNotification.service;

import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.notification.model.Notification;
import com.schoolsafetycrab.domain.notification.requestDto.CreateNotificationRequestDto;
import com.schoolsafetycrab.domain.pushnotification.dao.fcm.FCMDao;
import com.schoolsafetycrab.domain.pushnotification.message.responseDto.PushNotificationResponseDto;
import com.schoolsafetycrab.domain.pushnotification.model.PushNotification;
import com.schoolsafetycrab.domain.pushnotification.repository.PushNotificationRepository;
import com.schoolsafetycrab.domain.pushnotification.requestDto.SaveDeviceTokenRequestDto;
import com.schoolsafetycrab.domain.pushnotification.service.ObserverService;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.model.User;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ObserverServiceTest {

    @InjectMocks
    private ObserverService observerService;

    @Mock
    private PushNotificationRepository pushNotificationRepository;

    @Mock
    private FCMDao fcmDao;

    private User user;
    private Group group;
    private Notification notification;
    private PushNotification pushNotification;
    private PushNotificationResponseDto responseDto;

    @BeforeEach
    public void init(){
        user = User.createUser("test", "test", "test", "test", Role.ROLE_STUDENT, "010-1234-5678");
        group = Group.createGroup(new CreateGroupRequestDto("한밭초등학교", 4, 2, 20, "12345"));
        notification = Notification.createNotification(group, new CreateNotificationRequestDto(1, "태풍 조심", "태풍 조심해서 등교하기", LocalDate.parse("2024-06-28")));
        pushNotification = PushNotification.createUserDevice(user, new SaveDeviceTokenRequestDto("asfdawoeaccasera"));
        responseDto = PushNotificationResponseDto.createPushNotificationResponseDto(pushNotification, notification);
    }

    @Test
    @DisplayName("푸시알림 전송 성공 테스트")
    public void 푸시알림_전송_성공_테스트(){
        // given
        BDDMockito.given(pushNotificationRepository.findByUser_UserId(user.getUserId())).willReturn(Optional.of(pushNotification));

        // when
        Assertions.assertThatNoException().isThrownBy(() -> observerService.notifyUser(user, notification));

        // then
        BDDMockito.then(pushNotificationRepository).should().findByUser_UserId(user.getUserId());
        BDDMockito.then(fcmDao).should().pushMessage(BDDMockito.any(PushNotificationResponseDto.class));
    }

    @Test
    @DisplayName("디바이스 토큰 없음 예외 테스트")
    public void 디바이스_토큰_없음_예외_테스트() {
        // given
        BDDMockito.given(pushNotificationRepository.findByUser_UserId(user.getUserId())).willReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> observerService.notifyUser(user, notification))
                .isInstanceOf(ExceptionResponse.class)
                .hasFieldOrPropertyWithValue("customException", CustomException.NOT_FOUND_DEVICETOKEN_EXCEPTION);

        BDDMockito.then(pushNotificationRepository).should().findByUser_UserId(user.getUserId());
    }
}
