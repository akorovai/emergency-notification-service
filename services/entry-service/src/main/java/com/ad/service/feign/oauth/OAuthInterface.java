package com.ad.service.feign.oauth;

import com.ad.service.entry.EntryController;
import com.ad.service.feign.oauth.request.LoginRequest;
import com.ad.service.feign.oauth.request.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("OAUTH-SERVICE")
public interface OAuthInterface {

    @PostMapping("api/auth/register")
    ResponseEntity<EntryController.DefaultResponse> register(@RequestBody @Valid RegisterRequest request);
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request);
    @PostMapping("/refresh-jwt")
    ResponseEntity<EntryController.DefaultResponse> getNewToken(@RequestParam(name = "refresh-token") String refreshToken);

}
