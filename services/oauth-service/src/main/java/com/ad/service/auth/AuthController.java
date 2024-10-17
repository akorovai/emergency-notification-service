package com.ad.service.auth;

import com.ad.service.auth.request.LoginRequest;
import com.ad.service.auth.request.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        var res = authService.register(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        var res = authService.verify(request);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/refresh-jwt")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getNewToken(@RequestParam(name = "refresh-token") String refreshToken) {
        var res = authService.renewAccessToken(refreshToken);
        return ResponseEntity.ok().body(res);
    }

}
