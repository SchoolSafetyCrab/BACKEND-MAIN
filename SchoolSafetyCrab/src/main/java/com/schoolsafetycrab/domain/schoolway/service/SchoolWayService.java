package com.schoolsafetycrab.domain.schoolway.service;

import com.schoolsafetycrab.domain.schoolway.message.responseDto.PointResponseDto;
import com.schoolsafetycrab.domain.schoolway.model.SchoolWay;
import com.schoolsafetycrab.domain.schoolway.repository.SchoolWayRepository;
import com.schoolsafetycrab.domain.schoolway.requestDto.PointRequestDto;
import com.schoolsafetycrab.domain.schoolway.requestDto.SchoolWayPointRequestDto;
import com.schoolsafetycrab.domain.schoolwaypoint.model.SchoolWayPoint;
import com.schoolsafetycrab.domain.schoolwaypoint.repository.SchoolWayPointRepository;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import com.schoolsafetycrab.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SchoolWayService {

    private final SchoolWayRepository schoolWayRepository;
    private final SchoolWayPointRepository schoolWayPointRepository;

    @Transactional
    public void saveSchoolWay(Authentication authentication, SchoolWayPointRequestDto requestDto) {
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        if(!user.getRole().equals(Role.ROLE_STUDENT)){
            throw new ExceptionResponse(CustomException.ACCESS_DENIEND_EXCEPTION);
        }

        SchoolWay schoolWay = SchoolWay.createSchoolWay(user);
        schoolWayRepository.save(schoolWay);

        List<PointRequestDto> points = requestDto.getPoints();

        for(PointRequestDto point : points){
            SchoolWayPoint schoolWayPoint = SchoolWayPoint.createSchoolWayPoint(schoolWay, point.getLatitude(), point.getLongitude());
            schoolWayPointRepository.save(schoolWayPoint);
        }
    }

    @Transactional
    public void deleteSchoolWay(Authentication authentication){

        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();
        SchoolWay schoolWay = schoolWayRepository.findByUser(user).orElseThrow(() -> {
            throw new ExceptionResponse(CustomException.NOT_FOUND_SCHOOL_WAY_EXCEPTION);
        });

        schoolWayPointRepository.deleteAllBySchoolWay(schoolWay);
        schoolWayRepository.deleteBySchoolWayId(schoolWay.getSchoolWayId());
    }

    @Transactional
    public List<PointResponseDto> findMySchoolWay(Authentication authentication){
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();
        List<PointResponseDto> points = schoolWayPointRepository.findByUser(user);

        return points;
    }
}
