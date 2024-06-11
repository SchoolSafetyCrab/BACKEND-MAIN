package com.schoolsafetycrab.domain.numberAuth.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckAuthCodeRequestDto {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String authCode;
}
