package com.schoolsafetycrab.domain.group.requestDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateGroupRequestDto {

    @NotBlank
    private String schoolName;

    @Min(1)
    @Max(6)
    @NotNull
    private int schoolYear;

    @NotNull
    private int schoolBan;

    @NotNull
    private int userNum;

    @NotBlank
    private String groupCode;
}
