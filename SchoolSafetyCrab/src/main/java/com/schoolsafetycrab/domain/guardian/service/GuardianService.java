package com.schoolsafetycrab.domain.guardian.service;

import com.schoolsafetycrab.domain.guardian.message.responseDto.MyChildrenResponseDto;
import com.schoolsafetycrab.domain.guardian.repository.GuardianRepository;
import com.schoolsafetycrab.domain.schoolway.message.responseDto.PointResponseDto;
import com.schoolsafetycrab.domain.schoolwaypoint.model.SchoolWayPoint;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.user.repository.UserRepository;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
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
    private final UserRepository userRepository;

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

    public List<PointResponseDto> findMyChildrenSchoolWay(Authentication authentication, long userId){
        User user = ((PrincipalDetails)authentication.getPrincipal()).getUser();

        if(!userRepository.existsUserByUserIdAndState(userId, true)){
            throw new ExceptionResponse(CustomException.NOT_FOUND_USER_EXCEPTION);
        };

        if(!guardianRepository.existsGuardianByUser_userIdAndId(userId, user.getId())){
            throw new ExceptionResponse(CustomException.NOT_FOUND_CHILDREN_ID_EXCEPTION);
        }

        List<SchoolWayPoint> points = guardianRepository.findSchoolWayByMyChildren(userId);

        List<PointResponseDto> responses = new ArrayList<>();

        for(SchoolWayPoint point : points){
            responses.add(PointResponseDto.createPointResponseDto(point));
        }

        return responses;
    }
}
