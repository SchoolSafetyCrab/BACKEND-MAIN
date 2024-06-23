package com.schoolsafetycrab.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolsafetycrab.domain.user.repository.UserRepository;
import com.schoolsafetycrab.global.security.exceptionHandler.CustomExceptionHandler;
import com.schoolsafetycrab.global.security.jwt.JwtAuthenticationFilter;
import com.schoolsafetycrab.global.security.jwt.JwtAuthorizationFilter;
import com.schoolsafetycrab.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomExceptionHandler customExceptionHandler;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(objectMapper,jwtProvider);
        filter.setFilterProcessesUrl("/api/login");
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter(jwtProvider,userRepository);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());
        http.cors(Customizer.withDefaults());
        http.sessionManagement((sessionManagement) ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.formLogin((formLogin) -> formLogin.disable());
        http.authorizeHttpRequests((request) -> request
                .requestMatchers("/api/join/**").permitAll()
                .requestMatchers("/api/login").permitAll()
                .requestMatchers("/api/declaration").hasAnyRole("STUDENT","TEACHER","PARENTS")
                .requestMatchers("/api/user").hasAnyRole("STUDENT","TEACHER","PARENTS")
                .requestMatchers("/api/student/**").hasRole("STUDENT")
                .requestMatchers("/api/teacher/**").hasRole("TEACHER")
                .requestMatchers("/api/parents/**").hasRole("PARENTS")
                .requestMatchers("/api/common/**").hasAnyRole("STUDENT","TEACHER")
                .anyRequest().denyAll()
        );
        http.exceptionHandling((handle) -> handle.authenticationEntryPoint(customExceptionHandler));
        http.addFilterBefore(jwtAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
