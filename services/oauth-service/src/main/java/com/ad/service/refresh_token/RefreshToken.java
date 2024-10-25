package com.ad.service.refresh_token;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    private String token;
    private LocalDateTime expires;

    public static RefreshToken generateToken(long expiration) {
        return RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expires(LocalDateTime.now().plusSeconds(expiration / 1000))
                .build();
    }
}
