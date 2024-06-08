package com.schoolsafetycrab.domain.user.service;

import com.schoolsafetycrab.domain.numberAuth.repository.NumberAuthRepository;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.repository.UserRepository;
import com.schoolsafetycrab.domain.user.requestDto.SignUpRequestDto;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
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

import static org.mockito.ArgumentMatchers.any;
@ExtendWith(MockitoExtension.class)
public class UserSignUpServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private NumberAuthRepository numberAuthRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private SignUpRequestDto requestDto;

    @BeforeEach
    public void setup(){
        requestDto = new SignUpRequestDto("test","test","test","test", Role.ROLE_STUDENT,"010-1111-1111");
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void 회원가입_테스트(){
        //given
        BDDMockito.given(numberAuthRepository.existsBuSuccessNumber(any())).willReturn(true);
        BDDMockito.given(userRepository.existsUserById(any())).willReturn(false);
        BDDMockito.given(userRepository.existsUserByPhoneNumber(any())).willReturn(false);

        // when
        Assertions.assertThatNoException().isThrownBy(() -> userService.saveUser(requestDto));

        //then
        BDDMockito.then(numberAuthRepository).should().existsBuSuccessNumber(requestDto.getPhoneNumber());
        BDDMockito.then(userRepository).should().existsUserById(requestDto.getId());
        BDDMockito.then(userRepository).should().existsUserByPhoneNumber(requestDto.getPhoneNumber());
    }

    @Test
    @DisplayName("회원가입 예외 테스트 인증 번호 불일치")
    public void 인증번호_불일치_회원가입_실패_테스트(){
        //given
        BDDMockito.given(numberAuthRepository.existsBuSuccessNumber(any())).willReturn(false);

        //When
        Assertions.assertThatThrownBy(() -> userService.saveUser(requestDto))
                .isInstanceOf(ExceptionResponse.class)
                .hasFieldOrPropertyWithValue("customException", CustomException.DUPLICATED_NUMBER_EXCEPTION);
        BDDMockito.then(userRepository).shouldHaveNoInteractions();
        BDDMockito.then(userRepository).shouldHaveNoInteractions();
        //then
        BDDMockito.then(numberAuthRepository).should().existsBuSuccessNumber(requestDto.getPhoneNumber());
    }

    @Test
    @DisplayName("회원가입 ID 존재 실패 테스트")
    public void 회원가입_ID_존재_실패_테스트(){
        //given
        BDDMockito.given(numberAuthRepository.existsBuSuccessNumber(any())).willReturn(true);
        BDDMockito.given(userRepository.existsUserById(any())).willReturn(true);

        // when
        Assertions.assertThatThrownBy(() -> userService.saveUser(requestDto))
                .isInstanceOf(ExceptionResponse.class)
                .hasFieldOrPropertyWithValue("customException", CustomException.DUPLICATED_ID_EXCEPTION);

        //then
        BDDMockito.then(numberAuthRepository).should().existsBuSuccessNumber(requestDto.getPhoneNumber());
        BDDMockito.then(userRepository).should().existsUserById(requestDto.getId());
    }

    @Test
    @DisplayName("회원가입 번호 존재 실패 테스트")
    public void 회원가입_번호_존재_실패_테스트(){
        //given
        BDDMockito.given(numberAuthRepository.existsBuSuccessNumber(any())).willReturn(true);
        BDDMockito.given(userRepository.existsUserById(any())).willReturn(false);
        BDDMockito.given(userRepository.existsUserByPhoneNumber(any())).willReturn(true);

        // when
        Assertions.assertThatThrownBy(() -> userService.saveUser(requestDto))
                .isInstanceOf(ExceptionResponse.class)
                .hasFieldOrPropertyWithValue("customException", CustomException.DUPLICATED_NUMBER_EXCEPTION);

        //then
        BDDMockito.then(numberAuthRepository).should().existsBuSuccessNumber(requestDto.getPhoneNumber());
        BDDMockito.then(userRepository).should().existsUserById(requestDto.getId());
        BDDMockito.then(userRepository).should().existsUserByPhoneNumber(requestDto.getPhoneNumber());
    }
}
