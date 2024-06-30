package com.schoolsafetycrab.domain.group.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.model.QGroup;
import com.schoolsafetycrab.domain.schoolway.model.QSchoolWay;
import com.schoolsafetycrab.domain.schoolwaypoint.model.QSchoolWayPoint;
import com.schoolsafetycrab.domain.schoolwaypoint.model.SchoolWayPoint;
import lombok.RequiredArgsConstructor;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private QGroup group = QGroup.group;
    private QSchoolWayPoint schoolWayPoint;
    private QSchoolWay schoolWay;

    @Override
    public List<Group> findGroupByKeyword(String keyword)  {

        return queryFactory
                .select(group)
                .from(group)
                .where(group.schoolName.contains(keyword)
                        .and(group.state.eq(true)))
                .fetch();
    }

    @Override
    public List<SchoolWayPoint> findSchoolWayByStudent(long userId) {
        return queryFactory
                .select(schoolWayPoint)
                .from(schoolWayPoint)
                .join(schoolWayPoint.schoolWay, schoolWay)
                .where(schoolWay.user.userId.eq(userId))
                .fetch();
    }
}
