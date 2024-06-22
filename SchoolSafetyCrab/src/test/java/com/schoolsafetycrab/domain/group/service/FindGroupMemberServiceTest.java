package com.schoolsafetycrab.domain.group.service;

import com.schoolsafetycrab.domain.group.message.responseDto.GroupInfoResponseDto;
import com.schoolsafetycrab.domain.group.message.responseDto.GroupMemberResponseDto;
import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.repository.GroupRepository;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.model.User;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class FindGroupMemberServiceTest {

    @InjectMocks
    private GroupService groupService;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserGroupRepository userGroupRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private PrincipalDetails principalDetails;

    private User student;
    private User teacher;
    private Group group;
    private List<User> userList;

    @BeforeEach
    public void init() {
        group = Group.createGroup(new CreateGroupRequestDto
                ("한밭초등학교", 4, 2, 20, "12345"));
        student = User.createUser("test", "test", "test", "test", Role.ROLE_STUDENT, "010-1234-5678");
        userList = new ArrayList<>();
        userList.add(student);

        teacher = User.createUser("test1", "test1", "test1", "test", Role.ROLE_TEACHER, "010-1111-5678");
    }

    @Test
    @DisplayName("그룹 멤버 조회 성공 테스트")
    public void 그룹_멤버_조회_성공_테스트(){
        //given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(teacher);
        BDDMockito.given(userGroupRepository.findGroupMemberByGroupId(anyLong())).willReturn(userList);
        BDDMockito.given(userGroupRepository.existsByUser_UserIdAndGroup_GroupId(anyLong(), anyLong()))
                .willReturn(true);

        //when
        List<GroupMemberResponseDto> responses = groupService.findGroupMembers(authentication, group.getGroupId());

        //then
        Assertions.assertThat(responses.size()).isEqualTo(1);
        BDDMockito.then(userGroupRepository).should().existsByUser_UserIdAndGroup_GroupId(teacher.getUserId(), group.getGroupId());

    }

    @Test
    @DisplayName("그룹 관리 권한없음 실패 테스트")
    public void 그룹_관리_권한없음_실패_테스트() {
        // given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(teacher);
        BDDMockito.given(userGroupRepository.existsByUser_UserIdAndGroup_GroupId(anyLong(), anyLong()))
                .willReturn(false);

        // when
        Assertions.assertThatThrownBy(() -> groupService.findGroupMembers(authentication, group.getGroupId()))
                .isInstanceOf(ExceptionResponse.class)
                .hasFieldOrPropertyWithValue("customException", CustomException.ACCESS_DENIEND_EXCEPTION);

        // then
        BDDMockito.then(userGroupRepository).should().existsByUser_UserIdAndGroup_GroupId(teacher.getUserId(), group.getGroupId());
    }
}
