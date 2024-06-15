package com.schoolsafetycrab.domain.declaration.repository;

import com.schoolsafetycrab.domain.declaration.model.Declaration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeclarationRepository extends JpaRepository<Declaration, Long> {
}
