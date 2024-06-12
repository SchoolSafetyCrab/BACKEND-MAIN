package com.schoolsafetycrab.domain.guardian.repository;

import com.schoolsafetycrab.domain.guardian.model.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian,Long> {
}
