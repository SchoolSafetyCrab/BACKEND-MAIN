package com.schoolsafetycrab.global.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class MessageValueConfig {

    private DefaultMessageService defaultMessageService;

    @Value("${message.apiKey}")
    private String apiKey;

    @Value("${message.apiSecretKey}")
    private String apiSecretKey;

    @PostConstruct
    public void init() {
        this.defaultMessageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }
}
