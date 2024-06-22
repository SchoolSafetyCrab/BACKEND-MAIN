package com.schoolsafetycrab.domain.group.repository;

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
public class GroupRepositoryCustomImpl implements GroupRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private QGroup group = QGroup.group;

    @Override
    public List<Group> findGroupByKeyword(String keyword) {
        return queryFactory
                .select(group)
                .from(group)
                .where(group.schoolName.contains(keyword)
                        .and(group.state.eq(true)))
                .fetch();
    }
}
