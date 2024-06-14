package com.schoolsafetycrab.domain.declarationImg.repository;

import com.schoolsafetycrab.domain.declarationImg.model.DeclarationImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeclarationImgRepository extends JpaRepository<DeclarationImg, Long> {
}
