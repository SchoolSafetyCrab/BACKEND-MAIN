package com.schoolsafetycrab.domain.usergroup.repository;

import com.schoolsafetycrab.domain.usergroup.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

}
