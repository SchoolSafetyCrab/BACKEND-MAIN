package com.schoolsafetycrab.domain.user.model;

import com.schoolsafetycrab.global.baseTimeEntity.BaseTimeEntity;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ErrorResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "icon_img", nullable = false)
    private String iconImg;

    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "phone_number", nullable = false, length = 13)
    private String phoneNumber;

    @Column(name = "guardian_code", nullable = false)
    private String guardianCode;

    @Column(name = "state", nullable = false)
    private boolean state;

    @Builder
    private User(String id, String password, String nickname, String iconImg, Role role, String phoneNumber, String guardianCode, boolean state) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.iconImg = iconImg;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.guardianCode = guardianCode;
        this.state = state;
    }

    public static User createUser(String id, String password, String nickname, String iconImg, Role role, String phoneNumber, String guardianCode, boolean state){
        return User.builder()
                .id(id)
                .password(password)
                .nickname(nickname)
                .iconImg(iconImg)
                .role(role)
                .phoneNumber(phoneNumber)
                .guardianCode(guardianCode)
                .state(state)
                .build();
    }

    public List<Role> getMemberRoles(){
        if(this.role != null){
            return Arrays.asList(this.role);
        }
        throw new ErrorResponse(CustomException.NOT_EMPTY_ROLE_EXCEPTION);
    }
}
