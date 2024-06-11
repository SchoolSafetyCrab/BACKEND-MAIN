package com.schoolsafetycrab.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ROLE_STUDENT("ROLE_STUDENT"),
    ROLE_TEACHER("ROLE_TEACHER"),
    ROLE_PARENTS("ROLE_PARENTS");

    private String role;
}
