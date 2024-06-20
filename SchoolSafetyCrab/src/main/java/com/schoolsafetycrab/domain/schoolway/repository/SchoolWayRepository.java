package com.schoolsafetycrab.domain.schoolway.repository;

import com.schoolsafetycrab.domain.schoolway.model.SchoolWay;
import com.schoolsafetycrab.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolWayRepository extends JpaRepository<SchoolWay, Long> {

    Optional<SchoolWay> findByUser(User user);
    void deleteBySchoolWayId(Long schoolId);
}
