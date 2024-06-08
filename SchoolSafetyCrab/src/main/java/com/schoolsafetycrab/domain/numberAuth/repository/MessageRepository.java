package com.schoolsafetycrab.domain.numberAuth.repository;

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
}