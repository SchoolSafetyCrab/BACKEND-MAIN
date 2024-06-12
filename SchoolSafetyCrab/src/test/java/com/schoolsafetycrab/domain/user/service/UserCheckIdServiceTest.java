package com.schoolsafetycrab.domain.user.service;

import com.schoolsafetycrab.domain.numberAuth.repository.NumberAuthRepository;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.repository.UserRepository;
import com.schoolsafetycrab.domain.user.requestDto.CheckIdRequestDto;
import com.schoolsafetycrab.domain.user.requestDto.SignUpRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserCheckIdServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private NumberAuthRepository numberAuthRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private CheckIdRequestDto requestDto;

    @BeforeEach
    public void setup(){
        requestDto = new CheckIdRequestDto("test");
    }

    @Test
    @DisplayName("아이디 중복 체크 중복 아닐시 테스트")
    public void 아이디_중복_체크_중복_아닐시(){
        //given
        BDDMockito.given(userRepository.existsUserById(requestDto.getId())).willReturn(false);

        //when
        boolean check = userService.checkId(requestDto);

        //then
        Assertions.assertThat(check).isTrue();
    }


}
