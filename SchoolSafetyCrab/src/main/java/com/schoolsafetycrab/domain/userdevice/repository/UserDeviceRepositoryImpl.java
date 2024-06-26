package com.schoolsafetycrab.domain.userdevice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.schoolsafetycrab.domain.userdevice.model.QUserDevice;
import com.schoolsafetycrab.domain.userdevice.model.UserDevice;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDeviceRepositoryImpl implements UserDeviceRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    private QUserDevice qUserDevice = QUserDevice.userDevice;

    @Override
    public long updateDeviceToken(UserDevice userDevice) {
        return queryFactory.update(qUserDevice)
                .set(qUserDevice.deviceToken, userDevice.getDeviceToken())
                .where(qUserDevice.user.eq(userDevice.getUser()))
                .execute();
    }

}
