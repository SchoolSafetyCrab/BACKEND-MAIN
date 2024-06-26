package com.schoolsafetycrab.domain.userdevice.repository;

import com.schoolsafetycrab.domain.userdevice.model.UserDevice;

public interface UserDeviceRepositoryCustom {
    long updateDeviceToken(UserDevice userDevice);
}
