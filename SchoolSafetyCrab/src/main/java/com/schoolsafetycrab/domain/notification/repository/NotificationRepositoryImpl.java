package com.schoolsafetycrab.domain.notification.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.model.QGroup;
import com.schoolsafetycrab.domain.notification.model.QNotification;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    private QNotification notification = QNotification.notification;

    @Override
    public long updateStateByEndDate(LocalDate currentDate) {
        return queryFactory.update(notification)
                .set(notification.state, false)
                .where(notification.endDate.before(currentDate))
                .execute();
    }
}
