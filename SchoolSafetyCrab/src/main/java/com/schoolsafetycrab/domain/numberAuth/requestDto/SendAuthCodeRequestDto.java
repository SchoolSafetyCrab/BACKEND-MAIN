package com.schoolsafetycrab.domain.numberAuth.requestDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SendAuthCodeRequestDto {

    private String phoneNumber;
}
