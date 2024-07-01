package com.schoolsafetycrab.domain.group.message.responseDto;

import com.schoolsafetycrab.domain.user.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupMemberResponseDto {

    long userId;

    String id;

    String nickname;

    String iconImg;

    public GroupMemberResponseDto(long userId, String id, String nickname, String iconImg) {
        this.userId = userId;
        this.nickname = nickname;
        this.iconImg = iconImg;
    }

    public static GroupMemberResponseDto createGroupMemberResponseDto(User user){
        return new GroupMemberResponseDto(user.getUserId(), user.getId(), user.getNickname(), user.getIconImg());
    }
}
