package com.schoolsafetycrab.domain.usergroup.repository;

import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.user.model.User;

import java.util.List;

public interface UserGroupRepositoryCustom {
    List<Group> findGroupByUserId(Long userId);
    List<User> findGroupMemberByGroupId(Long groupId);
}
