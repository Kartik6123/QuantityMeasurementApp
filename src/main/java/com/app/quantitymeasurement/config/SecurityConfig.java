package com.app.quantitymeasurement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for development.
 * Permits all requests and disables CSRF so the H2 console, Swagger UI,
 * Actuator endpoints, and REST API are all accessible without authentication.
 *
 * UC17 note: This is a foundation class. In production, replace with
 * JWT / OAuth2 authentication by restricting the permitAll() rules.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for REST API and H2 console
            .csrf(AbstractHttpConfigurer::disable)
            // Allow H2 console to render inside an iframe
            .headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            // Permit everything — authentication to be added in a future UC
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll());

        return http.build();
    }
}
