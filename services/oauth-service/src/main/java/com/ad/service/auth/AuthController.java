package com.ad.service.auth;

import com.ad.service.auth.request.LoginRequest;
import com.ad.service.auth.request.RegisterRequest;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DefaultResponse> register(@RequestBody @Valid RegisterRequest request) {
        var res = DefaultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message(authService.register(request))
                .build();
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        var res = authService.verify(request);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/refresh-jwt")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DefaultResponse> getNewToken(@RequestParam(name = "refresh-token") String refreshToken) {
        var res = DefaultResponse.builder()
                .code(HttpStatus.OK.value())
                .message(authService.renewAccessToken(refreshToken))
                .build();
        return ResponseEntity.ok().body(res);
    }

    @Builder
    public record DefaultResponse(int code, String message) {
    }
}
