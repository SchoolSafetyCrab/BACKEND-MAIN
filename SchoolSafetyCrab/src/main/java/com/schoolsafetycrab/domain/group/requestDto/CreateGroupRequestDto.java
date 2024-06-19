package com.schoolsafetycrab.domain.group.requestDto;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private int schoolYear;

    @NotBlank
    private int schoolBan;

    @NotBlank
    private int userNum;

    @NotBlank
    private String groupCode;
}
