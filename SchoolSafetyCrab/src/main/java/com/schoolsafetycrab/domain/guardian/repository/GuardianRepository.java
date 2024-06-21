package com.schoolsafetycrab.domain.guardian.repository;

import com.schoolsafetycrab.domain.guardian.model.Guardian;
import com.schoolsafetycrab.domain.schoolwaypoint.model.SchoolWayPoint;
import com.schoolsafetycrab.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian,Long>, GuardianRepositoryCustom {

    boolean existsGuardianByUserAndId(User user, String id);
    boolean existsGuardianByUser_userIdAndId(long userId, String id);

    @Override
    List<User> findByMyChildren(String id);

    @Override
    List<SchoolWayPoint> findSchoolWayByMyChildren(long userId);
}
