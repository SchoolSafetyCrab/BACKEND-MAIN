package com.schoolsafetycrab.domain.group.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolsafetycrab.domain.group.message.SuccessGroupMessage;
import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
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
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
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

import java.util.HashMap;
import java.util.Map;

@WebMvcTest(
        controllers = GroupController.class,
        excludeFilters = {@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
@MockBean(JpaMetamodelMappingContext.class)
public class GroupControllerTest {

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

    private CreateGroupRequestDto createGroupRequestDto;

    @BeforeEach
    public void init(){
        createGroupRequestDto = new CreateGroupRequestDto
                ("한밭초등학교", 4, 2, 20, "12345");
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
        BDDMockito.doNothing().when(groupService).createGroup(authentication, createGroupRequestDto);
        BDDMockito.given(responseUtil.createResponse((SuccessGroupMessage.SUCCESS_CREATE_GROUP)))
                .willReturn(ResponseEntity.ok().body(mockResponseData));

        // then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/teacher/group/create")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
