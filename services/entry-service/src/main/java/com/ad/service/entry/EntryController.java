package com.ad.service.entry;

import com.ad.service.feign.oauth.LoginResponse;
import com.ad.service.feign.oauth.OAuthInterface;
import com.ad.service.feign.oauth.request.LoginRequest;
import com.ad.service.feign.oauth.request.RegisterRequest;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/entry")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EntryController {

    private final OAuthInterface oAuthInterface;
    @Value("${links.google-auth}")
    private String googleOauth2Link;

    @PostMapping("/register")
    public ResponseEntity<DefaultResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return oAuthInterface.register(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return oAuthInterface.login(loginRequest);
    }

    @PostMapping("/refresh-jwt")
    public ResponseEntity<DefaultResponse> refreshJwt(@RequestParam(name = "refresh-token") String refreshToken) {
        return oAuthInterface.getNewToken(refreshToken);
    }

    @GetMapping("/login/oauth2/google")
    public ResponseEntity<Void> oauth2Google() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(googleOauth2Link));
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }


    @Builder
    public record DefaultResponse(int code, String message) {
    }

}
