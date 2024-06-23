package com.schoolsafetycrab.domain.group.service;

import com.schoolsafetycrab.domain.group.message.responseDto.GroupInfoResponseDto;
import com.schoolsafetycrab.domain.group.model.Group;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SearchGroupListServiceTest {

    @InjectMocks
    private GroupStudentService groupStudentService;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserGroupRepository userGroupRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private PrincipalDetails principalDetails;

    private CreateGroupRequestDto createGroupRequestDto;
    private User user;
    private List<Group> groupList;

    @BeforeEach
    public void init() {
        createGroupRequestDto = new CreateGroupRequestDto
                ("한밭초등학교", 4, 2, 20, "12345");

        groupList = new ArrayList<>();
        groupList.add(Group.createGroup(createGroupRequestDto));
    }


    @Test
    @DisplayName("키워드 기반 그룹 검색 성공 테스트")
    public void 키워드_기반_그룹_검색_성공_테스트(){
        //given
        String keyword = "한밭";
        BDDMockito.given(groupRepository.findGroupByKeyword(any())).willReturn(groupList);

        //when
        List<GroupInfoResponseDto> responses = groupStudentService.searchGroup(keyword);

        //then
        Assertions.assertThat(responses.size()).isEqualTo(1);
    }
}
