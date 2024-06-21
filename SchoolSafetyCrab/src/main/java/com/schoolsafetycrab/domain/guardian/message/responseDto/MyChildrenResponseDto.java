package com.schoolsafetycrab.domain.guardian.message.responseDto;

import com.schoolsafetycrab.domain.user.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MyChildrenResponseDto {

    private long userId;
    private String nickname;
    private String iconImg;

    public MyChildrenResponseDto(long userId, String nickname, String iconImg) {
        this.userId = userId;
        this.nickname = nickname;
        this.iconImg = iconImg;
    }

    public static MyChildrenResponseDto userToMyChildrenResponseDto(User user){
        return new MyChildrenResponseDto(user.getUserId(), user.getNickname(), user.getIconImg());
    }
}
