package com.schoolsafetycrab.global.security.jwt;

import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.global.config.JwtValueConfig;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.security.auth.PrincipalDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    private final JwtValueConfig config;

    public String generateAccessToken(Authentication authentication){
        User member = ((PrincipalDetails) authentication.getPrincipal()).getUser();
        return Jwts.builder()
                .setSubject(member.getId())
                .claim("id",member.getId())
                .setExpiration(tokenExpiresIn(Long.parseLong(config.getAccessTokenExpire())))
                .signWith(config.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, HttpServletRequest request){
        try{
            Jwts.parserBuilder().setSigningKey(config.getKey()).build().parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e){
            request.setAttribute("exception", CustomException.EXPIRED_JWT_EXCEPTION);
        }
        catch (JwtException | IllegalArgumentException exception){
            request.setAttribute("exception",CustomException.NOT_VALID_JWT_EXCEPTION);
        }
        return false;
    }

    public String getPayload(String token , HttpServletRequest request){
        try{
            return Jwts.parser()
                    .setSigningKey(config.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }catch (ExpiredJwtException e){
            request.setAttribute("exception", CustomException.EXPIRED_JWT_EXCEPTION);
        }catch (JwtException e){
            request.setAttribute("exception",CustomException.NOT_VALID_JWT_EXCEPTION);
        }
        return null;
    }

    private Date tokenExpiresIn(long expires){
        long now = (new Date()).getTime();
        Date dateExpiresIn = new Date(now + expires);
        return dateExpiresIn;
    }
}
