package com.ad.service.auth;

import com.ad.service.auth.request.LoginRequest;
import com.ad.service.auth.request.RegisterRequest;
import com.ad.service.handler.BadRefreshTokenException;
import com.ad.service.handler.ExistingUserException;
import com.ad.service.refresh_token.RefreshToken;
import com.ad.service.security.jwt.JwtService;
import com.ad.service.user.Role;
import com.ad.service.user.User;
import com.ad.service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    @Transactional
    public String register(RegisterRequest request) {
        log.info("Starting registration process for user with username: {} and email: {}", request.getUsername(), request.getEmail());
        checkForExistingUser(request.getUsername(), request.getEmail());

        User user = User
                .builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .roles(Set.of(Role.USER.getRoleName()))
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        log.info("New user created with nickname: {}", user.getUsername());

        User newUser = userRepository.save(user);
        log.info("User {} successfully saved to database with ID: {}", newUser.getUsername(), newUser.getId());
        return "Added new User::" + newUser.getId();
    }

    public LoginResponse verify(LoginRequest request) {
        log.info("Attempting to authenticate user: {}", request.getUsername());
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            log.warn("Authentication failed for user: {}. Reason: {}", request.getUsername(), e.getMessage());
            throw e;
        }

        var user = (User) auth.getPrincipal();

        log.info("User authenticated successfully: {}", user.getUsername());

        var claims = createClaims(user);

        var accessToken = jwtService.generateToken(claims, user);
        var refreshToken = RefreshToken.generateToken(refreshTokenExpiration);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);
        log.debug("JWT tokens generated for user: {}", user.getUsername());

        return LoginResponse.builder()
                .jwtToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    protected Map<String, Object> createClaims(User user) {
        log.debug("Created claims for user: {}", user.getUsername());
        return Map.of("username", user.getUsername());
    }

    private void checkForExistingUser(String username, String email) {
        userRepository.findUserByUsernameOrEmail(username, email)
                .ifPresent((u) -> {
                    throw new ExistingUserException("User with such username/email already exists.");
                });
    }

    public String renewAccessToken(String refreshToken) {
        var user = userRepository.findByNotExpiredRefreshToken(refreshToken, LocalDateTime.now())
                .orElseThrow(() -> new BadRefreshTokenException("Wrong refresh token."));

        var claims = createClaims(user);

        return jwtService.generateToken(claims, user);
    }
}
