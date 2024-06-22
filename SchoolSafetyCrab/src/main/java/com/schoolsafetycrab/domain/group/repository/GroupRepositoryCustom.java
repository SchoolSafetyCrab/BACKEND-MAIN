package com.schoolsafetycrab.domain.group.repository;

import com.schoolsafetycrab.domain.group.model.Group;

import java.util.List;

public interface GroupRepositoryCustom {
    List<Group> findGroupByUserId(Long userId);
}
