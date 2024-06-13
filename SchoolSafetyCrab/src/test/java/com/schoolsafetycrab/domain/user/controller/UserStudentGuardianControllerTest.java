package com.schoolsafetycrab.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.requestDto.DesignateGuardianRequestDto;
import com.schoolsafetycrab.domain.user.service.StudentService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

@WebMvcTest(
        controllers = StudentController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@MockBean(JpaMetamodelMappingContext.class)
public class UserStudentGuardianControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    @MockBean
    private HttpResponseUtil httpResponseUtil;

    @Mock
    private Authentication authentication;

    private DesignateGuardianRequestDto requestDto;

    @BeforeEach
    public void init(){
        requestDto = new DesignateGuardianRequestDto("test1");
    }

    @Test
    @DisplayName("보호자 지정 성공 테스트")
    @WithMockAuthUser(id = "test@gmail.com", roles = Role.ROLE_STUDENT)
    public void 보호자_지정_성공_테스트() throws Exception{
        //given
        String requestBody = objectMapper.writeValueAsString(requestDto);
        Map<String, Object> mockResponseData = new HashMap<>();

        //when
        BDDMockito.doNothing().when(studentService).designateGuardian(authentication,requestDto);

        //then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/student/designate/guardian")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
