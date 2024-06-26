package com.schoolsafetycrab.domain.userdevice.repository;

import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.userdevice.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long>, UserDeviceRepositoryCustom {
    boolean existsByUser_userId(User user);

    @Override
    long updateDeviceToken(UserDevice userDevice);


}
