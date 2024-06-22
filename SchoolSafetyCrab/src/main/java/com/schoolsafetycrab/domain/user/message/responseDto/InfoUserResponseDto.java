package com.schoolsafetycrab.domain.user.message.responseDto;

import com.schoolsafetycrab.domain.user.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class InfoUserResponseDto {

    private String nickName;
    private String userImg;

    private InfoUserResponseDto(String nickName, String userImg) {
        this.nickName = nickName;
        this.userImg = userImg;
    }

    public static InfoUserResponseDto createInfoUserResponseDto(User user){
        return new InfoUserResponseDto(user.getNickname(),user.getIconImg());
    }
}
