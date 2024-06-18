package com.schoolsafetycrab.domain.schoolway.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PointRequestDto {

    @NotBlank
    private String latitude;

    @NotBlank
    private String longitude;
}
