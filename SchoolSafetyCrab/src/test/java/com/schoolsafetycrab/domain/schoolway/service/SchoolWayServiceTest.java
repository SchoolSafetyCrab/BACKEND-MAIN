package com.schoolsafetycrab.domain.schoolway.service;

import com.schoolsafetycrab.domain.schoolway.model.SchoolWay;
import com.schoolsafetycrab.domain.schoolway.repository.SchoolWayRepository;
import com.schoolsafetycrab.domain.schoolway.requestDto.PointRequestDto;
import com.schoolsafetycrab.domain.schoolway.requestDto.SchoolWayPointRequestDto;
import com.schoolsafetycrab.domain.schoolwaypoint.repository.SchoolWayPointRepository;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SchoolWayServiceTest {

    @InjectMocks
    private SchoolWayService schoolWayService;

    @Mock
    private SchoolWayRepository schoolWayRepository;

    @Mock
    private SchoolWayPointRepository schoolWayPointRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private PrincipalDetails principalDetails;

    private SchoolWayPointRequestDto schoolWayPointRequestDto;
    private PointRequestDto pointRequestDto;
    private User student;
    private SchoolWay schoolWay;

    @BeforeEach
    public void init(){
        student = User.createUser("test","test","test","test", Role.ROLE_STUDENT,"010-1111-1111");
        pointRequestDto = new PointRequestDto("1", "2");
        List<PointRequestDto> points = new ArrayList<>();
        points.add(pointRequestDto);
        schoolWayPointRequestDto = new SchoolWayPointRequestDto(points);
        schoolWay = SchoolWay.createSchoolWay(student);
    }

    @Test
    @DisplayName("등하굣길 등록 테스트")
    public void 등하굣길_등록_성공_테스트(){
        //given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(student);

        //when
        Assertions.assertThatNoException().isThrownBy(()->schoolWayService.saveSchoolWay(authentication, schoolWayPointRequestDto));

    }

    @Test
    @DisplayName("등하굣길 삭제 테스트")
    public void 등하굣길_삭제_성공_테스트(){
        //given
        BDDMockito.given(authentication.getPrincipal()).willReturn(principalDetails);
        BDDMockito.given(principalDetails.getUser()).willReturn(student);
        BDDMockito.given(schoolWayRepository.findByUser(student)).willReturn(Optional.ofNullable(schoolWay));
        BDDMockito.doNothing().when(schoolWayPointRepository).deleteAllBySchoolWay(schoolWay);

        //when
        Assertions.assertThatNoException().isThrownBy(()->schoolWayService.deleteSchoolWay(authentication));
    }
}
