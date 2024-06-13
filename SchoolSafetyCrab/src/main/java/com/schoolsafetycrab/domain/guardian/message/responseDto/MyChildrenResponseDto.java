package com.schoolsafetycrab.domain.guardian.message.responseDto;

import com.schoolsafetycrab.domain.user.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MyChildrenResponseDto {

    private String userId;

    private MyChildrenResponseDto(String userId) {
        this.userId = userId;
    }

    public static MyChildrenResponseDto userToMyChildrenResponseDto(User user){
        return new MyChildrenResponseDto(user.getId());
    }
}
