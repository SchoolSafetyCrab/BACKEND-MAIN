package com.schoolsafetycrab.domain.declaration.service;

import com.schoolsafetycrab.domain.declaration.model.Declaration;
import com.schoolsafetycrab.domain.declaration.repository.DeclarationRepository;
import com.schoolsafetycrab.domain.declaration.requestDto.DeclarationRequestDto;
import com.schoolsafetycrab.domain.declarationImg.model.DeclarationImg;
import com.schoolsafetycrab.domain.declarationImg.repository.DeclarationImgRepository;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
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
    private final DeclarationImgRepository declarationImgRepository;

    @Transactional
    public void requestDeclaration(Authentication authentication, DeclarationRequestDto requestDto){
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        Declaration declaration = Declaration.createDeclaration(user,requestDto);
        declarationRepository.save(declaration);

        if(requestDto.getImages().size() ==0){
            throw new ExceptionResponse(CustomException.NOT_IMAGE_EXCEPTION);
        }
        for(String imgUrl : requestDto.getImages()){
            DeclarationImg declarationImg = DeclarationImg.createDeclarationImg(declaration,imgUrl);
            declarationImgRepository.save(declarationImg);
        }
    }
}
