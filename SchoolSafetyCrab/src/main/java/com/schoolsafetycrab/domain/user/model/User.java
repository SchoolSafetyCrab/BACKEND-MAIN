package com.schoolsafetycrab.domain.user.model;

import com.schoolsafetycrab.global.baseTimeEntity.BaseTimeEntity;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
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

    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "icon_img", nullable = false)
    private String iconImg;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "phone_number", nullable = false, length = 13, unique = true)
    private String phoneNumber;

    @Column(name = "state", nullable = false)
    private boolean state;

    @Builder
    private User(String id, String password, String nickname, String iconImg, Role role, String phoneNumber, boolean state) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.iconImg = iconImg;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.state = state;
    }

    public static User createUser(String id, String password, String nickname, String iconImg, Role role, String phoneNumber){
        return User.builder()
                .id(id)
                .password(password)
                .nickname(nickname)
                .iconImg(iconImg)
                .role(role)
                .phoneNumber(phoneNumber)
                .state(true)
                .build();
    }

    public List<Role> getMemberRoles(){
        if(this.role != null){
            return Arrays.asList(this.role);
        }
        throw new ExceptionResponse(CustomException.NOT_EMPTY_ROLE_EXCEPTION);
    }
}
