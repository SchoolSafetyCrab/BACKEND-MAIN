package com.schoolsafetycrab.domain.pushnotification.model;

import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.global.baseTimeEntity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_device")
public class PushNotification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_device_id")
    private long userDeviceId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "device_token", nullable = false)
    private String deviceToken;

    private PushNotification(User user, String deviceToken) {
        this.user = user;
        this.deviceToken = deviceToken;
    }

    public static PushNotification createUserDevice(User user, String deviceToken) {
        return new PushNotification(user, deviceToken);
    }
}
