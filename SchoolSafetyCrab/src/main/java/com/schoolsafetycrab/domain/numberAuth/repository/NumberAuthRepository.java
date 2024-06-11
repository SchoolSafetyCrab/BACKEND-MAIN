package com.schoolsafetycrab.domain.numberAuth.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
@Slf4j
public class NumberAuthRepository {

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
            return false;
        }
        return true;
    }

    public boolean existsBuSuccessNumber(String phoneNumber){
        ValueOperations<Object,Object> valueOperations = redisTemplate.opsForValue();
        String value = (String)valueOperations.get(phoneNumber+"auth");
        if(value == null){
            return false;
        }
        return true;
    }
}