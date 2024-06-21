package com.schoolsafetycrab.domain.guardian.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MyChildrenSchoolWayRequestDto {

    @NotNull
    long userId;
}
