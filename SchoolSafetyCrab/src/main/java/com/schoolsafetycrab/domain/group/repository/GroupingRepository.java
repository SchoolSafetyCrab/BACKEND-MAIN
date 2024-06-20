package com.schoolsafetycrab.domain.group.repository;

import com.schoolsafetycrab.domain.group.model.Grouping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupingRepository extends JpaRepository<Grouping, Long> {
}
