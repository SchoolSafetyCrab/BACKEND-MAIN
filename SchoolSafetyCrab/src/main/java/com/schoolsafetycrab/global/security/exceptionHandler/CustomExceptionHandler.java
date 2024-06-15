package com.schoolsafetycrab.global.security.exceptionHandler;

import com.schoolsafetycrab.global.exception.CustomException;
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
        CustomException exception = (CustomException) request.getAttribute("exception");
        if(exception == null){
            log.error(authException.getMessage());
            exception = CustomException.ACCESS_DENIEND_EXCEPTION;
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("{ \"message\" : \"" + exception.getErrorMessage()
                + "\", \"code\" : \"" +  exception.getErrorCode()
                + "\", \"statusNum\" : " + exception.getStatusNum()
                + "}");
    }
}
