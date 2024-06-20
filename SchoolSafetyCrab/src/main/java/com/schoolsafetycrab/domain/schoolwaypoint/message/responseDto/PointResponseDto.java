package com.schoolsafetycrab.domain.schoolwaypoint.message.responseDto;

import com.schoolsafetycrab.domain.schoolwaypoint.model.SchoolWayPoint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PointResponseDto {

    private String latitude;
    private String longitude;

    private PointResponseDto(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static PointResponseDto createPointResponseDto(SchoolWayPoint schoolWayPoint) {
        return new PointResponseDto(schoolWayPoint.getLatitude(), schoolWayPoint.getLongitude());
    }
}
