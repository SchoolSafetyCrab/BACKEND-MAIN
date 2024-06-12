package com.schoolsafetycrab.domain.numberAuth.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendAuthCodeRequestDto {

    private String phoneNumber;
}
