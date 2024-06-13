package com.schoolsafetycrab.domain.user.requestDto;

import com.schoolsafetycrab.domain.user.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    private String id;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    private String iconImg;

    @NotBlank
    private Role role;

    @NotBlank
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    private String phoneNumber;
}
