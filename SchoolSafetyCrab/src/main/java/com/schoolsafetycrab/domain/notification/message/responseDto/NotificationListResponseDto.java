package com.schoolsafetycrab.domain.notification.message.responseDto;

import com.schoolsafetycrab.domain.notification.model.Notification;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class NotificationListResponseDto {

    private long notifiationId;

    private String title;

    private String detail;

    private boolean state;

    private LocalDate startDate;

    private LocalDate endDate;

    public static NotificationListResponseDto createNotificationListResponseDto(Notification notification) {
        return new NotificationListResponseDto(notification.getNotificationId(), notification.getTitle(),
                notification.getDetail(), notification.isState(), notification.getStartDate(), notification.getEndDate());
    }

}
