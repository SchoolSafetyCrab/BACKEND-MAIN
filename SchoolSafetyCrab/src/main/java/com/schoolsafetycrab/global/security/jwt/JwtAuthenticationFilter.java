package com.schoolsafetycrab.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper objectMapper;
    private JwtProvider jwtProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            User user = objectMapper.readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getId(),user.getPassword());
            return getAuthenticationManager().authenticate(authenticationToken);
        }catch (IOException e){
            throw new ExceptionResponse(CustomException.ID_PASSWORD_INPUT_EXCEPTION);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        String accessToken = jwtProvider.generateAccessToken(authentication);
        log.info("accessToken : {}", accessToken);
        response.addHeader("Authorization","Bearer "+accessToken);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("Login Success");
    }
}