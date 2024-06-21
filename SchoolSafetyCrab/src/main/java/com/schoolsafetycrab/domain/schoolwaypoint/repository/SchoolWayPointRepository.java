package com.schoolsafetycrab.domain.schoolwaypoint.repository;

import com.schoolsafetycrab.domain.schoolway.model.SchoolWay;
import com.schoolsafetycrab.domain.schoolwaypoint.model.SchoolWayPoint;
import com.schoolsafetycrab.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolWayPointRepository extends JpaRepository<SchoolWayPoint, Long>, SchoolWayPointRepositoryCustom {

    void deleteAllBySchoolWay(SchoolWay schoolWay);

    @Override
    List<SchoolWayPoint> findByUser(User user);
}
