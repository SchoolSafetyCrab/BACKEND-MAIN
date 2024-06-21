package com.schoolsafetycrab.domain.schoolwaypoint.repository;

import com.schoolsafetycrab.domain.schoolwaypoint.model.SchoolWayPoint;
import com.schoolsafetycrab.domain.user.model.User;

import java.util.List;

public interface SchoolWayPointRepositoryCustom {

    List<SchoolWayPoint> findByUser(User user);
}
