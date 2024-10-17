package com.ad.service.refresh_token;

import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class RefreshToken {

    private String token;
    private LocalDateTime expires;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refresh_token_expiration;

    private static long REFRESH_TOKEN_EXPIRATION;

    @PostConstruct
    public void init() {
        REFRESH_TOKEN_EXPIRATION = refresh_token_expiration;
    }

    public static RefreshToken generateToken() {
        return RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expires(LocalDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRATION))
                .build();
    }
}
