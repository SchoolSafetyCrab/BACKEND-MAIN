package com.schoolsafetycrab.domain.PushNotification.service;

import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.notification.model.Notification;
import com.schoolsafetycrab.domain.notification.requestDto.CreateNotificationRequestDto;
import com.schoolsafetycrab.domain.pushnotification.model.PushNotification;
import com.schoolsafetycrab.domain.pushnotification.repository.PushNotificationRepository;
import com.schoolsafetycrab.domain.pushnotification.requestDto.SaveDeviceTokenRequestDto;
import com.schoolsafetycrab.domain.pushnotification.service.PushNotificationService;
import com.schoolsafetycrab.domain.pushnotification.service.SubjectService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PushNotificationServiceTest {

    @InjectMocks
    private PushNotificationService pushNotificationService;

    @Mock
    private SubjectService subjectService;

    @Mock
    private PushNotificationRepository pushNotificationRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private PrincipalDetails principalDetails;

    private SaveDeviceTokenRequestDto requestDto;
    private User user;
    private PushNotification pushNotification;

    private Group group;
    private Notification notification;
    private List<User> students;

    @BeforeEach
    public void init() {
        user = User.createUser("test", "test", "test", "test", Role.ROLE_STUDENT, "010-1234-5678");
        requestDto = new SaveDeviceTokenRequestDto("asdfaweasdfA");
        pushNotification = PushNotification.createUserDevice(user, requestDto);

        group = Group.createGroup(new CreateGroupRequestDto("한밭초등학교", 4, 2, 20, "12345"));
        notification = Notification.createNotification(group, new CreateNotificationRequestDto(1, "태풍 조심", "태풍 조심해서 등교하기", LocalDate.parse("2024-06-28")));
        students = new ArrayList<>();
        students.add(user);
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

    @Test
    @DisplayName("푸시알림 전송 성공 테스트")
    public void 푸시알림_전송_성공_테스트(){
        // given
        BDDMockito.given(subjectService.getGroupStudent(group.getGroupId())).willReturn(students);

        // when
        Assertions.assertThatNoException().isThrownBy(()->pushNotificationService.pushNotificationToStudents(group.getGroupId(), notification.getNotificationId()));

        // then
        BDDMockito.then(subjectService).should().getGroupStudent(group.getGroupId());
    }
}
