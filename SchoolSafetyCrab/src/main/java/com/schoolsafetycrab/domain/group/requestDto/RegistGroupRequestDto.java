package com.schoolsafetycrab.domain.group.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegistGroupRequestDto {
    @NotBlank
    private long groupId;

    @NotNull
    private String groupCode;
}
