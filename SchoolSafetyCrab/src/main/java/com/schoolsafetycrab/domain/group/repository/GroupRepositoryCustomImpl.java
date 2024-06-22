package com.schoolsafetycrab.domain.group.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.model.QGroup;
import com.schoolsafetycrab.domain.usergroup.model.QUserGroup;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GroupRepositoryCustomImpl implements GroupRepositoryCustom{
    private final JPAQueryFactory queryFactory;

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
}
