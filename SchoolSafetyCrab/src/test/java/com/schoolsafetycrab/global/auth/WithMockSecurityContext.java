package com.schoolsafetycrab.global.auth;

import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.global.security.auth.PrincipalDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockSecurityContext implements WithSecurityContextFactory<WithMockAuthUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockAuthUser mockUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        User user = User.createUser(mockUser.id(), "test","test","010-1111-1111",mockUser.roles(),"010-1111-1111");
        PrincipalDetails memberDetails = new PrincipalDetails(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetails,null,memberDetails.getAuthorities());
        context.setAuthentication(authentication);
        return context;
    }
}
