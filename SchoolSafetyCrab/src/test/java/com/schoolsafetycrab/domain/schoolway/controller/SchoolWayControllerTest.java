package com.schoolsafetycrab.domain.schoolway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolsafetycrab.domain.schoolway.message.SuccessSchoolWayMessage;
import com.schoolsafetycrab.domain.schoolway.requestDto.PointRequestDto;
import com.schoolsafetycrab.domain.schoolway.requestDto.SchoolWayPointRequestDto;
import com.schoolsafetycrab.domain.schoolway.service.SchoolWayService;
import com.schoolsafetycrab.domain.user.message.SuccessSignUpMessage;
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

import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(
        controllers = SchoolWayController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
@MockBean(JpaMetamodelMappingContext.class)
public class SchoolWayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SchoolWayService schoolWayService;

    @MockBean
    private HttpResponseUtil responseUtil;

    @Mock
    private Authentication authentication;

    private SchoolWayPointRequestDto schoolWayPointRequestDto;
    private PointRequestDto pointRequestDto;

    @BeforeEach
    public void init(){
        pointRequestDto = new PointRequestDto("1", "2");
        List<PointRequestDto> points = new ArrayList<>();
        points.add(pointRequestDto);
        schoolWayPointRequestDto = new SchoolWayPointRequestDto(points);
    }

    @Test
    @DisplayName("등하굣길 등록 테스트")
    @WithMockAuthUser(id = "test", roles = Role.ROLE_STUDENT)
    public void 등하굣길_등록_성공_테스트() throws Exception{
        //given
        String requestBody = objectMapper.writeValueAsString(schoolWayPointRequestDto);
        Map<String, Object> mockResponseData = new HashMap<>();
        mockResponseData.put("data", SuccessSchoolWayMessage.SUCCESS_SCHOOL_WAY);

        //when
        BDDMockito.doNothing().when(schoolWayService).saveSchoolWay(authentication, schoolWayPointRequestDto);
        BDDMockito.given(responseUtil.createResponse(eq(SuccessSchoolWayMessage.SUCCESS_SCHOOL_WAY)))
                .willReturn(ResponseEntity.ok().body(mockResponseData));

        //then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/student/save/schoolway")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
