package com.schoolsafetycrab.domain.guardian.repository;

import com.schoolsafetycrab.domain.guardian.model.Guardian;
import com.schoolsafetycrab.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian,Long> {

    boolean existsGuardianByUserAndId(User user, String id);

}
