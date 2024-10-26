package com.ad.service.feign.oauth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String jwtToken;
    private String refreshToken;
}
