package com.schoolsafetycrab.domain.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.notification.message.SuccessNotificationMessage;
import com.schoolsafetycrab.domain.notification.message.responseDto.NotificationListResponseDto;
import com.schoolsafetycrab.domain.notification.model.Notification;
import com.schoolsafetycrab.domain.notification.requestDto.CreateNotificationRequestDto;
import com.schoolsafetycrab.domain.notification.service.NotificationCommonService;
import com.schoolsafetycrab.domain.notification.service.NotificationTeacherService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebMvcTest(
        controllers = NotificationCommonController.class,
        excludeFilters = {@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
@MockBean(JpaMetamodelMappingContext.class)
public class NotificationCommonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificationCommonService notificationCommonService;

    @MockBean
    private HttpResponseUtil responseUtil;

    @Mock
    private Authentication authentication;

    private CreateNotificationRequestDto requestDto;
    private Notification notification;
    private Group group;
    private List<NotificationListResponseDto> notificationResponses;

    @BeforeEach
    public void init(){
        group = Group.createGroup(new CreateGroupRequestDto
                ("한밭초등학교", 4, 2, 20, "12345"));
        requestDto = new CreateNotificationRequestDto(1, "태풍 조심", "태풍 조심해서 등교하기", LocalDate.parse("2024-06-28"));

        notification = Notification.createNotification(group, requestDto);

        notificationResponses = new ArrayList<>();
        notificationResponses.add(NotificationListResponseDto.createNotificationListResponseDto(notification));
    }

    @Test
    @DisplayName("그룹 공지 조회 성공 테스트")
    @WithMockAuthUser(id="test", roles= Role.ROLE_TEACHER)
    public void 그룹_공지_조회_성공_테스트() throws Exception{
        // given

        Map<String, Object> mockResponseData = new HashMap<>();
        mockResponseData.put("data", notificationResponses);

        // when
        BDDMockito.given(notificationCommonService.findNotificationList(authentication, group.getGroupId())).willReturn(notificationResponses);
        BDDMockito.given(responseUtil.createResponse(notificationResponses))
                .willReturn(ResponseEntity.ok().body(mockResponseData));

        // then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/common/find/notification/" + group.getGroupId())
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
