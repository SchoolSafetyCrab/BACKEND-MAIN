package com.schoolsafetycrab.domain.guardian.service;

import com.schoolsafetycrab.domain.guardian.message.responseDto.MyChildrenResponseDto;
import com.schoolsafetycrab.domain.guardian.repository.GuardianRepository;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GuardianService {

    private final GuardianRepository guardianRepository;

    public List<MyChildrenResponseDto> myChildren(Authentication authentication){
        User user = ((PrincipalDetails)authentication.getPrincipal()).getUser();
        List<User> children = guardianRepository.findByMyChildren(user.getId());

        List<MyChildrenResponseDto> responses = new ArrayList<>();

        for(User child : children){
            MyChildrenResponseDto response = MyChildrenResponseDto.userToMyChildrenResponseDto(child);
            responses.add(response);
        }

        return responses;
    }

}