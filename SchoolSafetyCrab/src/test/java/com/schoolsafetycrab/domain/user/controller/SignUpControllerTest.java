package com.schoolsafetycrab.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolsafetycrab.domain.user.message.SuccessMessage;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.requestDto.SignUpRequestDto;
import com.schoolsafetycrab.domain.user.service.UserService;
import com.schoolsafetycrab.global.auth.WithMockAuthUser;
import com.schoolsafetycrab.global.config.SecurityConfig;
import com.schoolsafetycrab.global.util.HttpResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(
        controllers = SignUpController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@MockBean(JpaMetamodelMappingContext.class)
public class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private HttpResponseUtil httpResponseUtil;

    private SignUpRequestDto requestDto;

    @BeforeEach
    public void setup() {
        requestDto = new SignUpRequestDto("test", "test", "test", "test", Role.ROLE_STUDENT, "010-1111-1111");
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    @WithMockAuthUser(id = "test@gmail.com", roles = Role.ROLE_STUDENT)
    public void 회원가입_성공_테스트() throws Exception {
        //given
        String requestBody = objectMapper.writeValueAsString(requestDto);
        Map<String, Object> mockResponseData = new HashMap<>();

        //when
        BDDMockito.doNothing().when(userService).saveUser(requestDto);
        BDDMockito.given(httpResponseUtil.createResponse(eq(SuccessMessage.SUCCESS_SIGNUP)))
                .willReturn(ResponseEntity.ok().body(mockResponseData));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/join")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
