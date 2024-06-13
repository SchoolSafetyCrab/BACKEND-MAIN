package com.schoolsafetycrab.domain.declaration.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DeclarationRequestDto {

    @NotBlank
    private String latitude;

    @NotBlank
    private String longitude;

    @NotBlank
    private String title;

    @NotBlank
    private String detail;
}
