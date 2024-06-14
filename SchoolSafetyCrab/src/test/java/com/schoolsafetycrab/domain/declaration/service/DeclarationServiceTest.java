package com.schoolsafetycrab.domain.declaration.service;

import com.schoolsafetycrab.domain.declaration.model.Declaration;
import com.schoolsafetycrab.domain.declaration.repository.DeclarationRepository;
import com.schoolsafetycrab.domain.declaration.requestDto.DeclarationRequestDto;
import com.schoolsafetycrab.domain.declarationImg.repository.DeclarationImgRepository;
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

@ExtendWith(MockitoExtension.class)
public class DeclarationServiceTest {

    @InjectMocks
    private DeclarationService declarationService;

    @Mock
    private DeclarationRepository declarationRepository;

    @Mock
    private DeclarationImgRepository declarationImgRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private PrincipalDetails principalDetails;

    private User user;
    private DeclarationRequestDto requestDto;
    private Declaration declaration;
    private List<String> images;

    @BeforeEach
    public void init(){
        user = User.createUser("test","test","test","test", Role.ROLE_STUDENT,"010-1111-1111");
        images = new ArrayList<>();
    }



    @Test
    @DisplayName("신고 성공 테스트")
    public void 신고_성공_테스트(){
        //given
        images.add("test");
        requestDto = new DeclarationRequestDto("11","11","test","test",images);
        declaration = Declaration.createDeclaration(user,requestDto);
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(user);

        //when && then
        Assertions.assertThatNoException().isThrownBy(() -> declarationService.requestDeclaration(authentication,requestDto));
    }
}
