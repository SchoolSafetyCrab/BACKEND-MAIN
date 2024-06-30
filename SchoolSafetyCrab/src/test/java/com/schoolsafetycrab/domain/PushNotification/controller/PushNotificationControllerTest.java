package com.schoolsafetycrab.domain.PushNotification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolsafetycrab.domain.notification.message.SuccessNotificationMessage;
import com.schoolsafetycrab.domain.pushnotification.controller.PushNotificationController;
import com.schoolsafetycrab.domain.pushnotification.message.SuccessPushNotificationMessage;
import com.schoolsafetycrab.domain.pushnotification.requestDto.SaveDeviceTokenRequestDto;
import com.schoolsafetycrab.domain.pushnotification.service.PushNotificationService;
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

import java.util.HashMap;
import java.util.Map;

@WebMvcTest(
        controllers = PushNotificationController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
)
@MockBean(JpaMetamodelMappingContext.class)
public class PushNotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PushNotificationService pushNotificationService;

    @MockBean
    private HttpResponseUtil responseUtil;

    @Mock
    private Authentication authentication;

    private SaveDeviceTokenRequestDto requestDto;

    @BeforeEach
    public void init(){
        requestDto = new SaveDeviceTokenRequestDto("asdfasdfasdfasdf");
    }

    @Test
    @DisplayName("디바이스 토큰 저장 성공 테스트")
    @WithMockAuthUser(id="test", roles= Role.ROLE_STUDENT)
    public void 디바이스_토큰_저장_성공_테스트() throws Exception{
        // given
        String requestBody = objectMapper.writeValueAsString(requestDto);
        Map<String, Object> mockResponseData = new HashMap<>();
        mockResponseData.put("data", SuccessPushNotificationMessage.SUCCESS_SAVE_TOKEN);

        // when
        BDDMockito.doNothing().when(pushNotificationService).saveUserDeviceToken(authentication, requestDto);
        BDDMockito.given(responseUtil.createResponse((SuccessPushNotificationMessage.SUCCESS_SAVE_TOKEN)))
                .willReturn(ResponseEntity.ok().body(mockResponseData));

        // then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/student/save/deviceToken")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
