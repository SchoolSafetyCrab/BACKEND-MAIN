package com.schoolsafetycrab.domain.schoolwaypoint.repository;

import com.schoolsafetycrab.domain.schoolwaypoint.model.SchoolWayPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolWayPointRepository extends JpaRepository<SchoolWayPoint, Long> {

    void deleteAllBySchoolWayId(Long schoolWayId);
}
