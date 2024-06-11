package com.schoolsafetycrab.global.auth;

import com.schoolsafetycrab.domain.user.model.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockSecurityContext.class)
public @interface WithMockAuthUser {
    String id();
    Role roles();
}
