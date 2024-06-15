package com.schoolsafetycrab.domain.guardian.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.schoolsafetycrab.domain.guardian.model.QGuardian;
import com.schoolsafetycrab.domain.user.model.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GuardianRepositoryImpl implements GuardianRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    private QGuardian guardian = QGuardian.guardian;

    @Override
    public List<User> findByMyChildren(String id) {
        return queryFactory
                .select(guardian.user)
                .from(guardian)
                .where(guardian.id.eq(id).and(guardian.user.state.isTrue()))
                .fetch();
    }
}
