package com.schoolsafetycrab.domain.user.message.responseDto;

import com.schoolsafetycrab.domain.user.model.Role;
import com.schoolsafetycrab.domain.user.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserInfoResponseDto {

    private String nickName;
    private String userImg;
    private Role role;

    private UserInfoResponseDto(String nickName, String userImg, Role role) {
        this.nickName = nickName;
        this.userImg = userImg;
        this.role = role;
    }

    public static UserInfoResponseDto createUserInfoResponseDto(User user){
        return new UserInfoResponseDto(user.getNickname(),user.getIconImg(), user.getRole());
    }
}
