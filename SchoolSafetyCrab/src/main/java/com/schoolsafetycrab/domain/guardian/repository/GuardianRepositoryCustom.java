package com.schoolsafetycrab.domain.guardian.repository;

import com.schoolsafetycrab.domain.user.model.User;

import java.util.List;

public interface GuardianRepositoryCustom {

    List<User> findByMyChildren(String id);
}
