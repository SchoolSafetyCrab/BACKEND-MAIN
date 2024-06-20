package com.schoolsafetycrab.domain.schoolway.repository;

import com.schoolsafetycrab.domain.schoolway.model.SchoolWay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolWayRepository extends JpaRepository<SchoolWay, Long> {

    void deleteBySchoolWayId(Long schoolId);
}
