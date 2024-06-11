package com.schoolsafetycrab.global.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

@Configuration
@Getter
public class JwtValueConfig {

    private Key key;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-expire}")
    private String accessTokenExpire;

    @PostConstruct
    private void init(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
}
