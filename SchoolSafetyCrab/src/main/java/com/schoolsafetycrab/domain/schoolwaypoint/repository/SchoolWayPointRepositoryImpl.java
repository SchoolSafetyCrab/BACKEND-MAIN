package com.schoolsafetycrab.domain.schoolwaypoint.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.schoolsafetycrab.domain.schoolway.model.QSchoolWay;
import com.schoolsafetycrab.domain.schoolwaypoint.model.QSchoolWayPoint;
import com.schoolsafetycrab.domain.schoolwaypoint.model.SchoolWayPoint;
import com.schoolsafetycrab.domain.user.model.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SchoolWayPointRepositoryImpl implements SchoolWayPointRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private QSchoolWayPoint schoolWayPoint = QSchoolWayPoint.schoolWayPoint;

    private QSchoolWay schoolWay = QSchoolWay.schoolWay;

    @Override
    public List<SchoolWayPoint> findByUser(User user) {
        return queryFactory
                .select(schoolWayPoint)
                .from(schoolWayPoint)
                .join(schoolWayPoint.schoolWay, schoolWay)
                .where(schoolWay.user.eq(user))
                .fetch();
    }
}
