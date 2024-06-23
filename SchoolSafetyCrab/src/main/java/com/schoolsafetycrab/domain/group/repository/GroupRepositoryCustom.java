package com.schoolsafetycrab.domain.group.repository;

import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.user.model.User;

import java.util.List;

public interface GroupRepositoryCustom {
    List<Group> findGroupByKeyword(String keyword);
}
