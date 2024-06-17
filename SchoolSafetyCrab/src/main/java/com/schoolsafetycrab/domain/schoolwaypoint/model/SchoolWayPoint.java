package com.schoolsafetycrab.domain.schoolwaypoint.model;

import com.schoolsafetycrab.domain.schoolway.model.SchoolWay;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SchoolWayPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_way_point_id")
    private Long schoolWayPointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schoolway_id")
    private SchoolWay schoolWay;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    private SchoolWayPoint(SchoolWay schoolWay, String latitude, String longitude) {
        this.schoolWay = schoolWay;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static SchoolWayPoint createSchoolWayPoint(SchoolWay schoolWay, String latitude, String longitude) {
        return new SchoolWayPoint(schoolWay, latitude, longitude);
    }
}
