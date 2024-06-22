package com.schoolsafetycrab.domain.group.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolsafetycrab.domain.group.message.responseDto.GroupInfoResponseDto;
import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.group.message.SuccessGroupMessage;
import com.schoolsafetycrab.domain.group.requestDto.RegistGroupRequestDto;
import com.schoolsafetycrab.domain.group.service.GroupService;
import com.schoolsafetycrab.domain.user.model.Role;
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

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(
        controllers = GroupStudentController.class,
        excludeFilters = {@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
@MockBean(JpaMetamodelMappingContext.class)
public class GroupStudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GroupService groupService;

    @MockBean
    private HttpResponseUtil responseUtil;

    @Mock
    private Authentication authentication;

    private RegistGroupRequestDto registGroupRequestDto;
    private Group group;

    private CreateGroupRequestDto createGroupRequestDto;
    private List<GroupInfoResponseDto> groupResponse;

    @BeforeEach
    public void init(){

        registGroupRequestDto = new RegistGroupRequestDto(1, "12345");

        createGroupRequestDto = new CreateGroupRequestDto("한밭초등학교", 3, 3, 25, "12345");
        group = Group.createGroup(createGroupRequestDto);

        groupResponse = new ArrayList<>();
        groupResponse.add(GroupInfoResponseDto.createGroupInfoResponseDto(group));
    }

    @Test
    @DisplayName("그룹 등록 성공 테스트")
    @WithMockAuthUser(id="test", roles= Role.ROLE_STUDENT)
    public void 그룹_등록_성공_테스트() throws Exception{
        // given
        String requestBody = objectMapper.writeValueAsString(registGroupRequestDto);
        Map<String, Object> mockResponseData = new HashMap<>();
        mockResponseData.put("data", SuccessGroupMessage.SUCCESS_REGIST_GROUP);

        // when
        BDDMockito.doNothing().when(groupService).registGroup(authentication, registGroupRequestDto);
        BDDMockito.given(responseUtil.createResponse((SuccessGroupMessage.SUCCESS_REGIST_GROUP)))
                .willReturn(ResponseEntity.ok().body(mockResponseData));

        // then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/student/regist/group")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("키워드 기반 그룹 검색 성공 테스트")
    @WithMockAuthUser(id="test", roles= Role.ROLE_STUDENT)
    public void 키워드_기반_그룹_검색_성공_테스트() throws Exception{
        //given
        String keyword = "한밭";
        Map<String, Object> mockResponseData = new HashMap<>();
        mockResponseData.put("data", groupResponse);

        BDDMockito.given(groupService.searchGroup(any())).willReturn(groupResponse);
        BDDMockito.given(responseUtil.createResponse(groupResponse))
                .willReturn(ResponseEntity.ok().body(mockResponseData));

        // then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/student/search/group?keyword="+keyword)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
