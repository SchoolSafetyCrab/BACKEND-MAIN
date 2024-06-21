package com.schoolsafetycrab.domain.group.service;

import com.schoolsafetycrab.domain.group.repository.GroupRepository;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.usergroup.repository.UserGroupRepository;
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

@ExtendWith(MockitoExtension.class)
public class CreateGroupServiceTest {

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

    private CreateGroupRequestDto createGroupRequestDto;
    private User teacher;

    @BeforeEach
    public void init() {
        teacher = User.createUser("test", "test", "test", "test", Role.ROLE_TEACHER, "010-1234-5678");
        createGroupRequestDto = new CreateGroupRequestDto
                ("한밭초등학교", 4, 2, 20, "12345");
    }

    @Test
    @DisplayName("그룹 생성 성공 테스트")
    public void 그룹_생성_성공_테스트(){
        //given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(teacher);

        //when
        Assertions.assertThatNoException().isThrownBy(()->groupService.createGroup(authentication, createGroupRequestDto));
    }
}
