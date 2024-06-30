package com.schoolsafetycrab.domain.group.repository;

import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.schoolwaypoint.model.SchoolWayPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, GroupRepositoryCustom {

    Optional<Group> findByGroupIdAndGroupCode(Long groupId, String groupCode);

    @Override
    List<Group> findGroupByKeyword(String keyword);

    @Override
    List<SchoolWayPoint> findSchoolWayByStudent(long userId);
}
