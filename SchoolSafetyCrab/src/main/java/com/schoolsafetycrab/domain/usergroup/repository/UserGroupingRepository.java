package com.schoolsafetycrab.domain.usergroup.repository;

import com.schoolsafetycrab.domain.usergroup.model.UserGrouping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupingRepository extends JpaRepository<UserGrouping, Long> {

}
