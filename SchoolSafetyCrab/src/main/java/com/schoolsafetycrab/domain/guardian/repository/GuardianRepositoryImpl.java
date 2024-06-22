package com.schoolsafetycrab.domain.guardian.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.schoolsafetycrab.domain.guardian.model.QGuardian;
import com.schoolsafetycrab.domain.schoolway.model.QSchoolWay;
import com.schoolsafetycrab.domain.schoolwaypoint.model.QSchoolWayPoint;
import com.schoolsafetycrab.domain.schoolwaypoint.model.SchoolWayPoint;
import com.schoolsafetycrab.domain.user.model.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GuardianRepositoryImpl implements GuardianRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    private QGuardian guardian = QGuardian.guardian;
    private QSchoolWayPoint schoolWayPoint = QSchoolWayPoint.schoolWayPoint;
    private QSchoolWay schoolWay = QSchoolWay.schoolWay;

    @Override
    public List<User> findByMyChildren(String id) {
        return queryFactory
                .select(guardian.user)
                .from(guardian)
                .where(guardian.id.eq(id).and(guardian.user.state.isTrue()))
                .fetch();
    }

    @Override
    public List<SchoolWayPoint> findSchoolWayByMyChildren(long userId) {
        return queryFactory
                .select(schoolWayPoint)
                .from(schoolWayPoint)
                .join(schoolWayPoint.schoolWay, schoolWay)
                .where(schoolWay.user.userId.eq(userId))
                .fetch();
    }
}
