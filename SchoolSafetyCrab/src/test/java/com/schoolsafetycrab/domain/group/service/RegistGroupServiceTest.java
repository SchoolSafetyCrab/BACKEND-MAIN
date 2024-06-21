package com.schoolsafetycrab.domain.group.service;

import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.repository.GroupRepository;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.group.requestDto.RegistGroupRequestDto;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RegistGroupServiceTest {

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

    private RegistGroupRequestDto registGroupRequestDto;
    private Group group;
    private User student;

    @BeforeEach
    public void init() {
        student = User.createUser("test", "test", "test", "test", Role.ROLE_STUDENT, "010-1234-5678");
        group = Group.createGroup(new CreateGroupRequestDto
                ("한밭초등학교", 4, 2, 20, "12345"));
        registGroupRequestDto = new RegistGroupRequestDto(0, "12345");
    }

    @Test
    @DisplayName("그룹 등록 성공 테스트")
    public void 그룹_등록_성공_테스트() {
        // given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(student);
        BDDMockito.given(userGroupRepository.existsByUser_UserIdAndGroup_GroupId(anyLong(), anyLong()))
                .willReturn(false);
        BDDMockito.given(groupRepository.findByGroupIdAndGroupCode(anyLong(), eq(group.getGroupCode())))
                .willReturn(Optional.ofNullable(group));
        BDDMockito.given(userGroupRepository.countByGroup_GroupId(anyLong())).willReturn(1L);

        // when
        Assertions.assertThatNoException().isThrownBy(() -> groupService.registGroup(authentication, registGroupRequestDto));

        // then
        BDDMockito.then(userGroupRepository).should().existsByUser_UserIdAndGroup_GroupId(student.getUserId(), registGroupRequestDto.getGroupId());
        BDDMockito.then(groupRepository).should().findByGroupIdAndGroupCode(registGroupRequestDto.getGroupId(), registGroupRequestDto.getGroupCode());
        BDDMockito.then(userGroupRepository).should().countByGroup_GroupId(registGroupRequestDto.getGroupId());

    }

    @Test
    @DisplayName("그룹 멤버 중복 실패 테스트")
    public void 그룹_멤버_중복_실패_테스트() {
        // given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(student);
        BDDMockito.given(userGroupRepository.existsByUser_UserIdAndGroup_GroupId(anyLong(), anyLong()))
                .willReturn(true);

        // when
        Assertions.assertThatThrownBy(() -> groupService.registGroup(authentication, registGroupRequestDto))
                .isInstanceOf(ExceptionResponse.class)
                .hasFieldOrPropertyWithValue("customException", CustomException.DUPLICATED_GROUP_MEMBER_EXCEPTION);

        // then
        BDDMockito.then(userGroupRepository).should().existsByUser_UserIdAndGroup_GroupId(student.getUserId(), registGroupRequestDto.getGroupId());
    }

    @Test
    @DisplayName("그룹 코드 불일치 실패 테스트")
    public void 그룹_코드_불일치_실패_테스트() {
        // given
        registGroupRequestDto = new RegistGroupRequestDto(0, "12345678");
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(student);
        BDDMockito.given(userGroupRepository.existsByUser_UserIdAndGroup_GroupId(anyLong(), anyLong()))
                .willReturn(false);
        BDDMockito.given(groupRepository.findByGroupIdAndGroupCode(anyLong(), any()))
                .willReturn(Optional.empty());

        // when
        Assertions.assertThatThrownBy(() -> groupService.registGroup(authentication, registGroupRequestDto))
                .isInstanceOf(ExceptionResponse.class)
                .hasFieldOrPropertyWithValue("customException", CustomException.NOT_MATCH_GROUP_CODE_EXCEPTION);

        // then
        BDDMockito.then(userGroupRepository).should().existsByUser_UserIdAndGroup_GroupId(student.getUserId(), registGroupRequestDto.getGroupId());
        BDDMockito.then(groupRepository).should().findByGroupIdAndGroupCode(registGroupRequestDto.getGroupId(), registGroupRequestDto.getGroupCode());
    }

    @Test
    @DisplayName("그룹 인원 초과 실패 테스트")
    public void 그룹_인원_초과_실패_테스트() {
        // given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(student);
        BDDMockito.given(userGroupRepository.existsByUser_UserIdAndGroup_GroupId(anyLong(), anyLong()))
                .willReturn(false);
        BDDMockito.given(groupRepository.findByGroupIdAndGroupCode(anyLong(), eq(group.getGroupCode())))
                .willReturn(Optional.ofNullable(group));
        BDDMockito.given(userGroupRepository.countByGroup_GroupId(anyLong())).willReturn(20L);

        // when
        Assertions.assertThatThrownBy(() -> groupService.registGroup(authentication, registGroupRequestDto))
                .isInstanceOf(ExceptionResponse.class)
                .hasFieldOrPropertyWithValue("customException", CustomException.EXCEED_GROUP_MEMBER_LIMIT_EXCEPTION);

        // then
        BDDMockito.then(userGroupRepository).should().existsByUser_UserIdAndGroup_GroupId(student.getUserId(), registGroupRequestDto.getGroupId());
        BDDMockito.then(groupRepository).should().findByGroupIdAndGroupCode(registGroupRequestDto.getGroupId(), registGroupRequestDto.getGroupCode());
        BDDMockito.then(userGroupRepository).should().countByGroup_GroupId(registGroupRequestDto.getGroupId());
    }
}
