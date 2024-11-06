package com.da.kafkaproducerservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig {

//    private final AuthenticationProvider authenticationProvider;
//    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("**")
                            .permitAll();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .authenticationProvider(authenticationProvider);

        return http.build();
    }

}
