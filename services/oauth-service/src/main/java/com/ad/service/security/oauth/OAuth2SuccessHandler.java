package com.ad.service.security.oauth;

import com.ad.service.refresh_token.RefreshToken;
import com.ad.service.security.jwt.JwtService;
import com.ad.service.user.User;
import com.ad.service.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        String email = oauthToken.getPrincipal().getAttribute("email");
        log.info("User authenticated with email: {}", email);

        RefreshToken refreshToken = RefreshToken.generateToken(refreshTokenExpiration);
        log.info("Generated refresh token: {}", refreshToken.getToken());

        User user = userRepository.findUserByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(UUID.randomUUID().toString());
            log.info("New user created with username: {}", newUser.getUsername());
            return newUser;
        });

        String jwt = jwtService.generateToken(createClaims(user), user);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        response.setHeader("Authorization", "Bearer " + jwt);
        response.getWriter().write("JWT: " + jwt);
        response.getWriter().write("\nRefresh: " + refreshToken.getToken());
        response.setStatus(HttpServletResponse.SC_OK);

        log.info("Authentication successful for user: {}", user.getUsername());
    }


    protected Map<String, Object> createClaims(User user) {
        log.debug("Created claims for user: {}", user.getUsername());
        return Map.of("username", user.getUsername());
    }
}
