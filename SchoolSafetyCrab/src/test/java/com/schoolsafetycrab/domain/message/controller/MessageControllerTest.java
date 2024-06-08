package com.schoolsafetycrab.domain.message.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolsafetycrab.domain.numberAuth.controller.MessageController;
import com.schoolsafetycrab.domain.numberAuth.message.SuccessMessage;
import com.schoolsafetycrab.domain.numberAuth.service.MessageService;
import com.schoolsafetycrab.domain.user.model.Role;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(controllers = MessageController.class,  excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MessageService messageService;

    @MockBean
    private HttpResponseUtil httpResponseUtil;

    @Test
    @DisplayName("인증 번호 전송 성공시 테스트")
    @WithMockAuthUser(id = "test@gmail.com", roles = Role.ROLE_STUDENT)
    public void 인증번호_전송_성공() throws Exception{
        //given
        String phoneNumber = "01012341234";
        String requestBody = objectMapper.writeValueAsString(phoneNumber);
        Map<String, Object> mockResponseData = new HashMap<>();

        //when
        BDDMockito.doNothing().when(messageService).sendAuthCode(phoneNumber);
        BDDMockito.given(httpResponseUtil.createResponse(eq(SuccessMessage.SUCCESS_MESSAGE)))
                .willReturn(ResponseEntity.ok().body(mockResponseData));

        //then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/join/send/code")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
