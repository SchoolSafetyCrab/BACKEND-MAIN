package com.schoolsafetycrab.domain.schoolway.service;

import com.schoolsafetycrab.domain.schoolway.model.SchoolWay;
import com.schoolsafetycrab.domain.schoolway.repository.SchoolWayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SchoolWayService {

    private final SchoolWayRepository schoolWayRepository;

    @Transactional
    public SchoolWay saveSchoolWay(Authentication authentication, SchoolWay schoolWay) {

    }
}
