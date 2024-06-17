package com.schoolsafetycrab.domain.schoolway.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SchoolWayPointRequestDto {

    @NotNull
    private List<PointRequestDto> points;
}
