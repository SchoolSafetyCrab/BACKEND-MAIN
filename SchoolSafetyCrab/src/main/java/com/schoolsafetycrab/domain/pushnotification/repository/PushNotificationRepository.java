package com.schoolsafetycrab.domain.pushnotification.repository;

import com.schoolsafetycrab.domain.pushnotification.model.PushNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PushNotificationRepository extends JpaRepository<PushNotification, Long>, PushNotificationRepositoryCustom {
    boolean existsByUser_UserId(long userId);

    PushNotification findByUser_UserId(long userId);

    @Override
    long updateDeviceToken(PushNotification pushNotification);
}
