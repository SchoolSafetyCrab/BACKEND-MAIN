package com.schoolsafetycrab.domain.notification.service;

import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.notification.message.responseDto.NotificationListResponseDto;
import com.schoolsafetycrab.domain.notification.model.Notification;
import com.schoolsafetycrab.domain.notification.repository.NotificationRepository;
import com.schoolsafetycrab.domain.notification.requestDto.CreateNotificationRequestDto;
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
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class NotificationCommonServiceTest {

    @InjectMocks
    private NotificationCommonService notificationCommonService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserGroupRepository userGroupRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private PrincipalDetails principalDetails;

    private CreateNotificationRequestDto createNotificationRequestDto;
    private User teacher;
    private Group group;
    private List<Notification> notificationResponses;


    @BeforeEach
    public void init() {
        teacher = User.createUser("test", "test", "test", "test", Role.ROLE_TEACHER, "010-1234-5678");

        group = Group.createGroup(new CreateGroupRequestDto
                ("한밭초등학교", 4, 2, 20, "12345"));
        createNotificationRequestDto = new CreateNotificationRequestDto(1, "태풍 조심", "태풍 조심해서 등교하기", LocalDate.parse("2024-06-28"));

        notificationResponses = new ArrayList<>();
        notificationResponses.add(Notification.createNotification(group, createNotificationRequestDto));
    }

    @Test
    @DisplayName("그룹 공지 조회 성공 테스트")
    public void 그룹_공지_조회_성공_테스트(){
        //given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(teacher);
        BDDMockito.given(userGroupRepository.existsByUser_UserIdAndGroup_GroupId(teacher.getUserId(), group.getGroupId()))
                .willReturn(true);
        BDDMockito.given(notificationRepository.updateStateByEndDate(LocalDate.now())).willReturn(1L);
        BDDMockito.given(notificationRepository.findByGroup_GroupId(group.getGroupId())).willReturn(notificationResponses);

        //when
        Assertions.assertThatNoException().isThrownBy(()-> notificationCommonService.findNotificationList(authentication, group.getGroupId()));

        BDDMockito.then(userGroupRepository).should().existsByUser_UserIdAndGroup_GroupId(teacher.getUserId(), group.getGroupId());
        BDDMockito.then(notificationRepository).should().updateStateByEndDate(LocalDate.now());
        BDDMockito.then(notificationRepository).should().findByGroup_GroupId(group.getGroupId());

    }
}
