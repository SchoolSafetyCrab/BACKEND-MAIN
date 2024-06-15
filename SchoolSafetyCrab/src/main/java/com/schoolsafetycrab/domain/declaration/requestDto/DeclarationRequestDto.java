package com.schoolsafetycrab.domain.declaration.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @NotNull
    @Size(min = 1)
    List<String> images;
}
