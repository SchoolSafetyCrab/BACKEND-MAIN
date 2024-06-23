package com.schoolsafetycrab.domain.group.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolsafetycrab.domain.group.message.SuccessGroupMessage;
import com.schoolsafetycrab.domain.group.message.responseDto.GroupMemberResponseDto;
import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.group.service.GroupCommonService;
import com.schoolsafetycrab.domain.group.service.GroupTeacherService;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.global.auth.WithMockAuthUser;
import com.schoolsafetycrab.global.config.SecurityConfig;
import com.schoolsafetycrab.global.util.HttpResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebMvcTest(
        controllers = GroupTeacherController.class,
        excludeFilters = {@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
@MockBean(JpaMetamodelMappingContext.class)
public class GroupTeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GroupTeacherService groupTeacherService;

    @MockBean
    private HttpResponseUtil responseUtil;

    @Mock
    private Authentication authentication;

    private User user;
    private Group group;
    private CreateGroupRequestDto createGroupRequestDto;
    private List<GroupMemberResponseDto> groupMemberResponse;

    @BeforeEach
    public void init(){
        user = User.createUser("test", "test", "test", "test", Role.ROLE_STUDENT, "010-1111-2222");
        createGroupRequestDto = new CreateGroupRequestDto
                ("한밭초등학교", 4, 2, 20, "12345");
        group = Group.createGroup(createGroupRequestDto);

        groupMemberResponse = new ArrayList<>();
        groupMemberResponse.add(GroupMemberResponseDto.createGroupMemberResponseDto(user));
    }


    @Test
    @DisplayName("그룹 생성 성공 테스트")
    @WithMockAuthUser(id="test", roles= Role.ROLE_TEACHER)
    public void 그룹_생성_성공_테스트() throws Exception{
        // given
        String requestBody = objectMapper.writeValueAsString(createGroupRequestDto);
        Map<String, Object> mockResponseData = new HashMap<>();
        mockResponseData.put("data", SuccessGroupMessage.SUCCESS_CREATE_GROUP);

        // when
        BDDMockito.doNothing().when(groupTeacherService).createGroup(authentication, createGroupRequestDto);
        BDDMockito.given(responseUtil.createResponse((SuccessGroupMessage.SUCCESS_CREATE_GROUP)))
                .willReturn(ResponseEntity.ok().body(mockResponseData));

        // then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/teacher/create/group")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("그룹 멤버 정보 조회 성공 테스트")
    @WithMockAuthUser(id="test", roles= Role.ROLE_TEACHER)
    public void 그룹_멤버_정보_조회_성공_테스트() throws Exception{
        //given
        Map<String, Object> mockResponseData = new HashMap<>();
        mockResponseData.put("data", groupMemberResponse);

        BDDMockito.given(groupTeacherService.findGroupMembers(authentication, group.getGroupId()))
                .willReturn(groupMemberResponse);
        BDDMockito.given(responseUtil.createResponse(groupMemberResponse))
                .willReturn(ResponseEntity.ok().body(mockResponseData));

        // then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/member/group/" + group.getGroupId())
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
