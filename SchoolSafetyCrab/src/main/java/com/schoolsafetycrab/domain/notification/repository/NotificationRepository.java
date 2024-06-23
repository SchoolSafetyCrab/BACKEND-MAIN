package com.schoolsafetycrab.domain.notification.repository;

import com.schoolsafetycrab.domain.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    void deleteByNotificationId(Long notificationId);

}
