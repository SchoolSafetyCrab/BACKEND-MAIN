package com.schoolsafetycrab.domain.user.message.responseDto;

import com.schoolsafetycrab.domain.user.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserInfoResponseDto {

    private String nickName;
    private String userImg;

    private UserInfoResponseDto(String nickName, String userImg) {
        this.nickName = nickName;
        this.userImg = userImg;
    }

    public static UserInfoResponseDto createUserInfoResponseDto(User user){
        return new UserInfoResponseDto(user.getNickname(),user.getIconImg());
    }
}
