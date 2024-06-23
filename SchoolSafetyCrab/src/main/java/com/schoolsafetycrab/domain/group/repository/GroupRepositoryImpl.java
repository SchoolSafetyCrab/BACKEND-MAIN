package com.schoolsafetycrab.domain.group.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.model.QGroup;
import lombok.RequiredArgsConstructor;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private QGroup group = QGroup.group;

    @Override
    public List<Group> findGroupByKeyword(String keyword)  {

        return queryFactory
                .select(group)
                .from(group)
                .where(group.schoolName.contains(keyword)
                        .and(group.state.eq(true)))
                .fetch();
    }
}
