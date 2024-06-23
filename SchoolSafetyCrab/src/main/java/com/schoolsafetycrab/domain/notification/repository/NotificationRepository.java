package com.schoolsafetycrab.domain.notification.repository;

import com.schoolsafetycrab.domain.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByNotificationId(Long notificationId);

    void deleteByNotificationId(Long notificationId);

}
