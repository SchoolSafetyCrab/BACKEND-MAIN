package com.schoolsafetycrab.domain.user.service;

import com.schoolsafetycrab.domain.guardian.repository.GuardianRepository;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.user.repository.UserRepository;
import com.schoolsafetycrab.domain.user.requestDto.DesignateGuardianRequestDto;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserStudentGuardianTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GuardianRepository guardianRepository;

    @Mock
    private Authentication authentication;
    @Mock
    private PrincipalDetails principalDetails;

    private User user;
    private User guardian;
    private DesignateGuardianRequestDto requestDto;

    @BeforeEach
    public void init(){
        user = User.createUser("test","test","test","test", Role.ROLE_STUDENT,"010-1111-1111");
        guardian = User.createUser("test1","test","test","test", Role.ROLE_PARENTS,"010-1111-1112");
        requestDto = new DesignateGuardianRequestDto("test1");
    }
    @Test
    @DisplayName("보호자 지정 테스트")
    public void 보호자_지정_성공_테스트(){
        //given
        BDDMockito.given(userRepository.findUserByIdAndState(guardian.getId(),true)).willReturn(Optional.ofNullable(guardian));
        BDDMockito.given(guardianRepository.existsGuardianByUserAndId(user,guardian.getId())).willReturn(false);
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(user);

        //when
        Assertions.assertThatNoException().isThrownBy(() -> studentService.designateGuardian(authentication,requestDto));

        //then
        BDDMockito.then(userRepository).should().findUserByIdAndState(guardian.getId(),true);
        BDDMockito.then(guardianRepository).should().existsGuardianByUserAndId(user,guardian.getId());
    }

    @Test
    @DisplayName("보호자 지정 실패 테스트 보호자 아이디 존재하지 않음")
    public void 보호자_지정_실패_테스트_보호자_아이디_존재하지_않음_테스트(){
        //given
        BDDMockito.given(userRepository.findUserByIdAndState(guardian.getId(),true)).willThrow(new ExceptionResponse(CustomException.NOT_FOUND_USER_EXCEPTION));
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(user);

        //when
        Assertions.assertThatThrownBy(() -> studentService.designateGuardian(authentication,requestDto))
                .isInstanceOf(ExceptionResponse.class)
                .hasFieldOrPropertyWithValue("customException", CustomException.NOT_FOUND_USER_EXCEPTION);

        //then
        BDDMockito.then(userRepository).should().findUserByIdAndState(guardian.getId(),true);
        BDDMockito.then(authentication).should().getPrincipal();
        BDDMockito.then(principalDetails).should().getUser();
    }

    @Test
    @DisplayName("보호자 지정 실패 테스트 보호자 존재")
    public void 보호자_지정_실패_테스트_보호자_존재_테스트(){
        //given
        BDDMockito.given(userRepository.findUserByIdAndState(guardian.getId(),true)).willReturn(Optional.ofNullable(guardian));
        BDDMockito.given(guardianRepository.existsGuardianByUserAndId(user,guardian.getId())).willReturn(true);
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(user);
        //when
        Assertions.assertThatThrownBy(() -> studentService.designateGuardian(authentication,requestDto))
                .isInstanceOf(ExceptionResponse.class)
                .hasFieldOrPropertyWithValue("customException", CustomException.DUPLICATED_GUARDIAN_ID_EXCEPTION);

        //then
        BDDMockito.then(userRepository).should().findUserByIdAndState(guardian.getId(),true);
        BDDMockito.then(guardianRepository).should().existsGuardianByUserAndId(user,guardian.getId());
        BDDMockito.then(authentication).should().getPrincipal();
        BDDMockito.then(principalDetails).should().getUser();
    }
}
