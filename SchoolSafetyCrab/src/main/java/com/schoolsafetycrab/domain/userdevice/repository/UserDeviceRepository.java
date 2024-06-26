package com.schoolsafetycrab.domain.userdevice.repository;

import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.userdevice.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long>, UserDeviceRepositoryCustom {
    boolean existsByUser_UserId(long userId);

    Optional<UserDevice> findByUser_UserId(long userId);

    @Override
    long updateDeviceToken(UserDevice userDevice);


}
