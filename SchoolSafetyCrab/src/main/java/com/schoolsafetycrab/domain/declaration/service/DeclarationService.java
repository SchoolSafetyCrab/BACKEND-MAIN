package com.schoolsafetycrab.domain.declaration.service;

import com.schoolsafetycrab.domain.declaration.model.Declaration;
import com.schoolsafetycrab.domain.declaration.repository.DeclarationRepository;
import com.schoolsafetycrab.domain.declaration.requestDto.DeclarationRequestDto;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DeclarationService {

    private final DeclarationRepository declarationRepository;

    @Transactional
    public void requestDeclaration(Authentication authentication, DeclarationRequestDto requestDto){
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        Declaration declaration = Declaration.createDeclaration(user,requestDto);
        declarationRepository.save(declaration);
    }
}
