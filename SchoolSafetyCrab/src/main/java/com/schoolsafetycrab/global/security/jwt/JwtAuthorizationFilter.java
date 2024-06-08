package com.schoolsafetycrab.global.security.jwt;

import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.user.repository.UserRepository;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import com.schoolsafetycrab.global.security.auth.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private JwtProvider jwtTokenProvider;
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getAccessToken(request);
        if(accessToken!= null && userRepository.existsUserByIdAndState(getTokenPayLoad(accessToken,request),true)){
            String id = getTokenPayLoad(accessToken,request);
            User user = userRepository.findUserByIdAndState(id,true).orElseThrow(() -> new ExceptionResponse(CustomException.NOT_FOUND_USER_EXCEPTION));
            generateAuthentication(user);
        }
        filterChain.doFilter(request,response);
    }

    private String getAccessToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        log.info("bearerToken : {}", bearerToken);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    private String getTokenPayLoad(String token, HttpServletRequest request){
        if(jwtTokenProvider.validateToken(token,request)){
            return jwtTokenProvider.getPayload(token,request);
        }
        return null;
    }

    private void generateAuthentication(User user){
        PrincipalDetails userPrincipalDetails = new PrincipalDetails(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipalDetails,null, userPrincipalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
