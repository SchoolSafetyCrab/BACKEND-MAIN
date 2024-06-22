package com.schoolsafetycrab.domain.usergroup.repository;

import com.schoolsafetycrab.domain.usergroup.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    boolean existsByUser_UserIdAndGroup_GroupId(Long userId, Long groupId);

    long countByGroup_GroupId(Long groupId);

}
