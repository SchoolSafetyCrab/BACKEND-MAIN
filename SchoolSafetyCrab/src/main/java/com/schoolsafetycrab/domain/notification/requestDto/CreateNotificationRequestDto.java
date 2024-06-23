package com.schoolsafetycrab.domain.notification.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateNotificationRequestDto {

    @NotNull
    private long groupId;

    @NotBlank
    private String title;

    @NotBlank
    private String detail;

    @NotNull
    private LocalDate endDate;

}
