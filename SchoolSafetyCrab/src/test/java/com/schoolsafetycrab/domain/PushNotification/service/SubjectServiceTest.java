package com.schoolsafetycrab.domain.PushNotification.service;

import com.schoolsafetycrab.domain.group.message.responseDto.GroupMemberResponseDto;
import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.notification.model.Notification;
import com.schoolsafetycrab.domain.notification.repository.NotificationRepository;
import com.schoolsafetycrab.domain.notification.requestDto.CreateNotificationRequestDto;
import com.schoolsafetycrab.domain.pushnotification.service.ObserverService;
import com.schoolsafetycrab.domain.pushnotification.service.SubjectService;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.usergroup.repository.UserGroupRepository;
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
public class SubjectServiceTest {

    @InjectMocks
    private SubjectService subjectService;

    @Mock
    private ObserverService observerService;

    @Mock
    private UserGroupRepository userGroupRepository;

    @Mock
    private NotificationRepository notificationRepository;

    private User user;
    private Group group;
    private Notification notification;
    private List<User> students;

    @BeforeEach
    public void init(){
        user = User.createUser("test", "test", "test", "test", Role.ROLE_STUDENT, "010-1234-5678");
        group = Group.createGroup(new CreateGroupRequestDto("한밭초등학교", 4, 2, 20, "12345"));
        notification = Notification.createNotification(group, new CreateNotificationRequestDto(1, "태풍 조심", "태풍 조심해서 등교하기", LocalDate.parse("2024-06-28")));
        students = new ArrayList<>();
        students.add(user);
    }

    @Test
    @DisplayName("그룹 멤버 조회 성공 테스트")
    public void 그룹_멤버_조회_성공_테스트(){
        // given
        BDDMockito.given(userGroupRepository.findGroupMemberByGroupId(group.getGroupId())).willReturn(students);

        // when
        List<User> result = subjectService.getGroupStudent(group.getGroupId());

        // then
        Assertions.assertThat(result.size()).isEqualTo(1);
        BDDMockito.then(userGroupRepository).should().findGroupMemberByGroupId(group.getGroupId());
    }

    @Test
    @DisplayName("학생들에 조회 푸시알림 전송 성공 테스트")
    public void 학생들에_조회_푸시알림_전송_성공_테스트(){
        // given
        BDDMockito.given(notificationRepository.findByNotificationId(notification.getNotificationId())).willReturn(Optional.of(notification));

        // when
        Assertions.assertThatNoException().isThrownBy(() -> subjectService.notifyUser(students, notification.getNotificationId()));

        // then
        BDDMockito.then(notificationRepository).should().findByNotificationId(notification.getNotificationId());
        for (User student : students) {
            BDDMockito.then(observerService).should().notifyUser(student, notification);
        }
    }

    @Test
    @DisplayName("푸시알림 조회 실패 테스트")
    public void 푸시알림_조회_실패_테스트(){
        // given
        BDDMockito.given(notificationRepository.findByNotificationId(notification.getNotificationId())).willReturn(Optional.empty());

        // when
        Assertions.assertThatThrownBy(() -> subjectService.notifyUser(students, notification.getNotificationId()))
                .isInstanceOf(ExceptionResponse.class)
                .hasFieldOrPropertyWithValue("customException", CustomException.NOT_FOUND_NOTIFICATION_EXCEPTION);


        // then
        BDDMockito.then(notificationRepository).should().findByNotificationId(notification.getNotificationId());
    }
}
