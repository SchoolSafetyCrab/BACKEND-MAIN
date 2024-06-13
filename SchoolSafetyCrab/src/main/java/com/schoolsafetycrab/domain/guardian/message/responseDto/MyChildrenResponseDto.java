package com.schoolsafetycrab.domain.guardian.message.responseDto;

import com.schoolsafetycrab.domain.user.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyChildrenResponseDto {

    private String userId;

    private MyChildrenResponseDto(String userId) {
        this.userId = userId;
    }

    public MyChildrenResponseDto userToMyChildrenResponseDto(User user){
        return new MyChildrenResponseDto(user.getId());
    }
}
