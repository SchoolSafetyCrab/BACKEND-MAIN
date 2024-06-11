package com.schoolsafetycrab.domain.numberAuth.requestDto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckAuthCodeRequestDto {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String authCode;
}
