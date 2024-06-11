package com.schoolsafetycrab.global.security.exceptionHandler;

import com.schoolsafetycrab.global.exception.ExceptionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomExceptionHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ExceptionResponse exception = (ExceptionResponse) request.getAttribute("exception");

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("{ \"message\" : \"" + exception.getCustomException().getErrorMessage()
                + "\", \"code\" : \"" +  exception.getCustomException().getErrorCode()
                + "\", \"statusNum\" : " + exception.getCustomException().getStatusNum()
                + "}");
    }
}
