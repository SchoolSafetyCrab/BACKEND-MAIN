package com.schoolsafetycrab.domain.notification.service;

import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.notification.model.Notification;
import com.schoolsafetycrab.domain.notification.repository.NotificationRepository;
import com.schoolsafetycrab.domain.notification.requestDto.CreateNotificationRequestDto;
import com.schoolsafetycrab.domain.pushnotification.service.PushNotificationService;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.usergroup.model.UserGroup;
import com.schoolsafetycrab.domain.usergroup.repository.UserGroupRepository;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class NotificationTeacherServiceTest {

    @InjectMocks
    private NotificationTeacherService notificationTeacherService;

    @Mock
    private PushNotificationService pushNotificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserGroupRepository userGroupRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private PrincipalDetails principalDetails;

    private CreateNotificationRequestDto createNotificationRequestDto;
    private CreateGroupRequestDto createGroupRequestDto;
    private User teacher;
    private Group group;
    private UserGroup userGroup;
    private Notification notification;

    @BeforeEach
    public void init() {
        teacher = User.createUser("test", "test", "test", "test", Role.ROLE_TEACHER, "010-1234-5678");
        group = Group.createGroup(new CreateGroupRequestDto
                ("한밭초등학교", 4, 2, 20, "12345"));
        userGroup = UserGroup.createUserGroup(group, teacher);
        createNotificationRequestDto = new CreateNotificationRequestDto(1, "태풍 조심", "태풍 조심해서 등교하기", LocalDate.parse("2024-06-28"));
        notification = Notification.createNotification(group, createNotificationRequestDto);
    }

    @Test
    @DisplayName("공지 생성 성공 테스트")
    public void 공지_생성_성공_테스트(){
        //given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(teacher);
        BDDMockito.given(userGroupRepository.findByUser_UserIdAndGroup_GroupId(teacher.getUserId(), createNotificationRequestDto.getGroupId()))
                .willReturn(Optional.ofNullable(userGroup));
        BDDMockito.given(notificationRepository.save(any(Notification.class))).willReturn(notification);

        BDDMockito.doNothing().when(pushNotificationService).pushNotificationToStudents(group.getGroupId(), notification.getNotificationId());

        //when
        Assertions.assertThatNoException().isThrownBy(()-> notificationTeacherService.createNotification(authentication, createNotificationRequestDto));

        BDDMockito.then(userGroupRepository).should().findByUser_UserIdAndGroup_GroupId(teacher.getUserId(), createNotificationRequestDto.getGroupId());
        BDDMockito.then(notificationRepository).should().save(any(Notification.class));
    }

    @Test
    @DisplayName("공지 생성 권한없음 실패 테스트")
    public void 공지_생성_권한없음_실패_테스트() {
        // given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(teacher);
        BDDMockito.given(userGroupRepository.findByUser_UserIdAndGroup_GroupId(teacher.getUserId(), createNotificationRequestDto.getGroupId()))
                .willReturn(Optional.empty());

        // when
        Assertions.assertThatThrownBy(() -> notificationTeacherService.createNotification(authentication, createNotificationRequestDto))
                .isInstanceOf(ExceptionResponse.class)
                .hasFieldOrPropertyWithValue("customException", CustomException.ACCESS_DENIEND_EXCEPTION);

        // then
        BDDMockito.then(userGroupRepository).should().findByUser_UserIdAndGroup_GroupId(teacher.getUserId(), createNotificationRequestDto.getGroupId());

    }

    @Test
    @DisplayName("공지 삭제 성공 테스트")
    public void 공지_삭제_성공_테스트(){
        //given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(teacher);
        BDDMockito.given(notificationRepository.findByNotificationId(notification.getNotificationId()))
                .willReturn(Optional.ofNullable(notification));
        BDDMockito.given(userGroupRepository.existsByUser_UserIdAndGroup_GroupId(teacher.getUserId(), notification.getGroup().getGroupId()))
                .willReturn(true);

        //when
        Assertions.assertThatNoException().isThrownBy(()-> notificationTeacherService.deleteNotification(authentication, notification.getNotificationId()));

        BDDMockito.then(notificationRepository).should().findByNotificationId(notification.getNotificationId());
        BDDMockito.then(userGroupRepository).should().existsByUser_UserIdAndGroup_GroupId(teacher.getUserId(), notification.getGroup().getGroupId());
        BDDMockito.then(notificationRepository).should().deleteByNotificationId(notification.getNotificationId());

    }
}
