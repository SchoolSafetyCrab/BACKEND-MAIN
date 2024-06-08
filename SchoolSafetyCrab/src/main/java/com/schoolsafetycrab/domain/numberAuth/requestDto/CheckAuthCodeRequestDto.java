package com.schoolsafetycrab.domain.numberAuth.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CheckAuthCodeRequestDto {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String authCode;
}
