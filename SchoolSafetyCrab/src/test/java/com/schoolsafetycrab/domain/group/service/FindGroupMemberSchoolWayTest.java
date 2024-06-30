package com.schoolsafetycrab.domain.group.service;

import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.repository.GroupRepository;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.schoolway.message.responseDto.PointResponseDto;
import com.schoolsafetycrab.domain.schoolway.model.SchoolWay;
import com.schoolsafetycrab.domain.schoolwaypoint.model.SchoolWayPoint;
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

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FindGroupMemberSchoolWayTest {

    @InjectMocks
    private GroupTeacherService groupTeacherService;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserGroupRepository userGroupRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private PrincipalDetails principalDetails;

    private User teacher;
    private User student;
    private CreateGroupRequestDto createGroupRequestDto;
    private Group group;
    private List<SchoolWayPoint> schoolWayPoints;

    @BeforeEach
    public void init() {
        teacher = User.createUser("test", "test", "test", "test", Role.ROLE_TEACHER, "010-1234-5678");
        student = User.createUser("student", "test", "test", "test", Role.ROLE_STUDENT, "010-1234-2222");
        createGroupRequestDto = new CreateGroupRequestDto
                ("한밭초등학교", 4, 2, 20, "12345");
        group = Group.createGroup(createGroupRequestDto);
        schoolWayPoints = new ArrayList<>();
        schoolWayPoints.add(SchoolWayPoint.createSchoolWayPoint(SchoolWay.createSchoolWay(student), "1", "2"));
    }

    @Test
    @DisplayName("그룹 학생 등교길 조회 테스트 성공")
    public void 그룹_학생_등교길_조회_테스트_성공(){
        //given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(teacher);
        BDDMockito.given(userGroupRepository.existsByUser_UserIdAndGroup_GroupId(teacher.getUserId(),group.getGroupId())).willReturn(true);
        BDDMockito.given(userGroupRepository.existsByUser_UserIdAndGroup_GroupId(student.getUserId(),group.getGroupId())).willReturn(true);
        BDDMockito.given(groupRepository.findSchoolWayByStudent(student.getUserId())).willReturn(schoolWayPoints);
        //when
        List<PointResponseDto> responses = groupTeacherService.findGroupMemberSchoolWay(authentication,group.getGroupId(),student.getUserId());

        //then
        Assertions.assertThat(responses.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("그룹 학생 등교길 조회 테스트 선생님이 그룹에 없음 실패")
    public void 그룹_학생_등교길_조회_실패_테스트_선생님_그룹_존재하지_않음(){
        //given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(teacher);
        BDDMockito.given(userGroupRepository.existsByUser_UserIdAndGroup_GroupId(teacher.getUserId(),group.getGroupId())).willReturn(false);
        //when
        Assertions.assertThatThrownBy(() -> groupTeacherService.findGroupMemberSchoolWay(authentication,group.getGroupId(), student.getUserId()))
                .isInstanceOf(ExceptionResponse.class)
                        .hasFieldOrPropertyWithValue("customException", CustomException.ACCESS_DENIEND_EXCEPTION);
    }

    @Test
    @DisplayName("그룹 학생 등교길 조회 테스트 학생 그룹에 없음 실패")
    public void 그룹_학생_등교길_조회_실패_테스트_학생_그룹_존재하지_않음(){
        //given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(teacher);
        BDDMockito.given(userGroupRepository.existsByUser_UserIdAndGroup_GroupId(teacher.getUserId(),group.getGroupId())).willReturn(true);
        BDDMockito.given(userGroupRepository.existsByUser_UserIdAndGroup_GroupId(student.getUserId(),group.getGroupId())).willReturn(false);

        //when
        Assertions.assertThatThrownBy(() -> groupTeacherService.findGroupMemberSchoolWay(authentication,group.getGroupId(), student.getUserId()))
                .isInstanceOf(ExceptionResponse.class)
                .hasFieldOrPropertyWithValue("customException", CustomException.ACCESS_DENIEND_EXCEPTION);

        BDDMockito.then(userGroupRepository).should().existsByUser_UserIdAndGroup_GroupId(teacher.getUserId(), group.getGroupId());
    }
}
