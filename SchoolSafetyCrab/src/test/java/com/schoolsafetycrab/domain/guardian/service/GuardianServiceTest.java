package com.schoolsafetycrab.domain.guardian.service;

import com.schoolsafetycrab.domain.guardian.message.responseDto.MyChildrenResponseDto;
import com.schoolsafetycrab.domain.guardian.repository.GuardianRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class GuardianServiceTest {

    @InjectMocks
    private GuardianService guardianService;

    @Mock
    private GuardianRepository guardianRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private PrincipalDetails principalDetails;

    private User guardian;
    private List<User> children;

    @BeforeEach
    public void init(){
        guardian = User.createUser("test1","test","test","test", Role.ROLE_PARENTS,"010-1111-1112");
        children = new ArrayList<>();
        User  user = User.createUser("test","test","test","test", Role.ROLE_STUDENT,"010-1111-1111");
        children.add(user);
    }

    @Test
    @DisplayName("보호자 자식 조회 테스트")
    public void 보호자_자식_조회_성공_테스트(){
        //given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(guardian);
        BDDMockito.given(guardianRepository.findByMyChildren(any())).willReturn(children);

        //when
        List<MyChildrenResponseDto> responses = guardianService.myChildren(authentication);

        //then
        Assertions.assertThat(responses.size()).isEqualTo(1);
    }
}
