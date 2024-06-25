package com.schoolsafetycrab.domain.notification.repository;
import java.time.LocalDate;

public interface NotificationRepositoryCustom {

    long updateStateByEndDate(LocalDate currentDate);
}
