package com.schoolsafetycrab.domain.notification.model;

import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.notification.requestDto.CreateNotificationRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private long notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "detail", columnDefinition = "TEXT", nullable = false)
    private String detail;

    @Column(name = "state")
    private boolean state;

    @Column(name = "start_date", columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(name = "end_date", columnDefinition = "DATE")
    private LocalDate endDate;

    @Builder
    private Notification(Group group, String title, String detail, boolean state, LocalDate startDate, LocalDate endDate) {
        this.group = group;
        this.title = title;
        this.detail = detail;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Notification createNotification(CreateNotificationRequestDto requestDto, Group group) {
        return Notification.builder()
                .group(group)
                .title(requestDto.getTitle())
                .detail(requestDto.getDetail())
                .state(true)
                .startDate(LocalDate.now())
                .endDate(requestDto.getEndDate())
                .build();
    }
}
