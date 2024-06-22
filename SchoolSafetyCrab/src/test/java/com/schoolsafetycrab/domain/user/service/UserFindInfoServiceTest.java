package com.schoolsafetycrab.domain.user.service;

import com.schoolsafetycrab.domain.user.message.responseDto.UserInfoResponseDto;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.global.security.auth.PrincipalDetails;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class UserFindInfoServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private PrincipalDetails principalDetails;

    private User user;

    @BeforeEach
    public void init(){
        user = User.createUser("test1","test","test","test", Role.ROLE_PARENTS,"010-1111-1112");
    }

    @Test
    @DisplayName("유저 조회 성공 테스트")
    public void 유저_조회_성공_테스트(){
        //given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(user);

        //when
        UserInfoResponseDto response = userService.findUserInfo(authentication);

        //then
        Assertions.assertThat(response).isInstanceOf(UserInfoResponseDto.class);
    }
}
