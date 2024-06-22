package com.schoolsafetycrab.domain.usergroup.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.model.QGroup;
import com.schoolsafetycrab.domain.user.model.QUser;
import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.usergroup.model.QUserGroup;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserGroupRepositoryCustomImpl implements UserGroupRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    private QUser user = QUser.user;
    private QGroup group = QGroup.group;
    private QUserGroup userGroup = QUserGroup.userGroup;

    @Override
    public List<Group> findGroupByUserId(Long userId) {
        return queryFactory
                .select(group)
                .from(userGroup)
                .join(userGroup.group, group)
                .where(userGroup.user.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<User> findGroupMemberByGroupId(Long groupId) {
        return queryFactory
                .select(user)
                .from(userGroup)
                .join(userGroup.user, user)
                .where(userGroup.group.groupId.eq(groupId)
                        .and(user.state.eq(true))
                        .and(user.role.eq(Role.ROLE_STUDENT)))
                .fetch();
    }
}
