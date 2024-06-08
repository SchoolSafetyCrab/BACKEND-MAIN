package com.schoolsafetycrab.domain.numberAuth.repository;

import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MessageRepository {

    private final RedisTemplate<Object, Object> redisTemplate;

    public void saveAuth(String phoneNumber, Object authCode){
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(phoneNumber, authCode, Duration.ofMinutes(5));
    }

    public void saveSuccessNumber(String phoneNumber){
        ValueOperations<Object,Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(phoneNumber+"auth","success",Duration.ofMinutes(5));
    }

    public boolean checkAuth(String phoneNumber, String authCode){
        ValueOperations<Object,Object> valueOperations = redisTemplate.opsForValue();
        String value = (String)valueOperations.get(phoneNumber);
        if(!value.equals(authCode)){
            throw new ExceptionResponse(CustomException.NOT_MATCH_AUTH_CODE);
        }
        return true;
    }
}