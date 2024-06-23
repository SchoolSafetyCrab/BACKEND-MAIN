package com.schoolsafetycrab.domain.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.notification.message.SuccessNotificationMessage;
import com.schoolsafetycrab.domain.notification.model.Notification;
import com.schoolsafetycrab.domain.notification.requestDto.CreateNotificationRequestDto;
import com.schoolsafetycrab.domain.notification.service.NotificationService;
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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@WebMvcTest(
        controllers = NotificationController.class,
        excludeFilters = {@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
@MockBean(JpaMetamodelMappingContext.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private HttpResponseUtil responseUtil;

    @Mock
    private Authentication authentication;

    private CreateNotificationRequestDto requestDto;
    private Notification notification;
    private Group group;

    @BeforeEach
    public void init(){
        group = Group.createGroup(new CreateGroupRequestDto
                ("한밭초등학교", 4, 2, 20, "12345"));
        requestDto = new CreateNotificationRequestDto(1, "태풍 조심", "태풍 조심해서 등교하기", LocalDate.parse("2024-06-28"));

        notification = Notification.createNotification(group, requestDto);
    }

    @Test
    @DisplayName("공지 생성 성공 테스트")
    @WithMockAuthUser(id="test", roles= Role.ROLE_TEACHER)
    public void 공지_생성_성공_테스트() throws Exception{
        // given
        String requestBody = objectMapper.writeValueAsString(requestDto);
        Map<String, Object> mockResponseData = new HashMap<>();
        mockResponseData.put("data", SuccessNotificationMessage.SUCCESS_CREATE_NOTIFICATION);

        // when
        BDDMockito.doNothing().when(notificationService).createNotification(authentication, requestDto);
        BDDMockito.given(responseUtil.createResponse((SuccessNotificationMessage.SUCCESS_CREATE_NOTIFICATION)))
                .willReturn(ResponseEntity.ok().body(mockResponseData));

        // then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/teacher/create/notification")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("공지 삭제 성공 테스트")
    @WithMockAuthUser(id="test", roles= Role.ROLE_TEACHER)
    public void 공지_삭제_성공_테스트() throws Exception{
        // given
        Map<String, Object> mockResponseData = new HashMap<>();
        mockResponseData.put("data", SuccessNotificationMessage.SUCCESS_DELETE_NOTIFICATION);

        // when
        BDDMockito.doNothing().when(notificationService).deleteNotification(authentication, notification.getNotificationId());
        BDDMockito.given(responseUtil.createResponse((SuccessNotificationMessage.SUCCESS_DELETE_NOTIFICATION)))
                .willReturn(ResponseEntity.ok().body(mockResponseData));

        // then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/teacher/delete/notification/" + notification.getNotificationId())
                .with(SecurityMockMvcRequestPostProcessors.csrf())

        );

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
