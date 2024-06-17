package com.schoolsafetycrab.domain.schoolway.model;

import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.global.baseTimeEntity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "Schoolway")
public class SchoolWay extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schoolway_id")
    private long schoolWayId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private SchoolWay(User user, long schoolWayId) {
        this.user = user;
        this.schoolWayId = schoolWayId;
    }

    public static SchoolWay createSchoolWay(User user, long schoolWayId) {
        return new SchoolWay(user, schoolWayId);
    }
}
