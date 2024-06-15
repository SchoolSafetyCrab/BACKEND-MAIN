package com.schoolsafetycrab.domain.guardian.controller;

import com.schoolsafetycrab.domain.guardian.message.responseDto.MyChildrenResponseDto;
import com.schoolsafetycrab.domain.guardian.service.GuardianService;
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
        controllers = GuardianController.class,
        excludeFilters ={@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
@MockBean(JpaMetamodelMappingContext.class)
public class GuardianControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GuardianService guardianService;

    @MockBean
    private HttpResponseUtil httpResponseUtil;

    @Mock
    private Authentication authentication;

    List<MyChildrenResponseDto> responses;

    @BeforeEach
    public void init(){
        responses = new ArrayList<>();
        User user = User.createUser("test","test","test","test", Role.ROLE_STUDENT,"010-1111-1111");
        MyChildrenResponseDto responseDto = MyChildrenResponseDto.userToMyChildrenResponseDto(user);
        responses.add(responseDto);
    }

    @Test
    @DisplayName("보호자 자식 조회 테스트")
    @WithMockAuthUser(id = "test@gmail.com", roles = Role.ROLE_PARENTS)
    public void 보호자_자식_조회_성공_테스트() throws Exception{
        //given
        Map<String, Object> mockResponseData = new HashMap<>();
        mockResponseData.put("data",responses);

        BDDMockito.given(guardianService.myChildren(authentication)).willReturn(responses);
        BDDMockito.given(httpResponseUtil.createResponse(responses))
                .willReturn(ResponseEntity.ok().body(mockResponseData));

        //then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/parents")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
