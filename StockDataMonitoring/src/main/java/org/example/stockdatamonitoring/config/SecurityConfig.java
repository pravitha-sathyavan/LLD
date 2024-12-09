package org.example.stockdatamonitoring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
// OAuth authentication
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login").permitAll()  // Public access
                        .requestMatchers("/stockData/subscribe/symbol").authenticated()
                        .requestMatchers(HttpMethod.POST,"/stockData/subscribe/symbol/{symbolname}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/stockData/unsubscribe/{symbol}").authenticated()// Allow POST but require authentication// Allow POST but require authentication
                        .anyRequest().authenticated()                // All other endpoints require authentication
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")                          // Custom login page
                        .defaultSuccessUrl("/home", true)            // Redirect to home on success
                        .failureUrl("/login?error")                  // Redirect to login with error on failure
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(
                        "/stockData/subscribe/symbol",
                        "/stockData/unsubscribe/{symbol}",
                        "/stockData/subscribe/symbol/{symbolname}")); // Disable CSRF for specific POST
        return http.build();
    }
}

/*
.csrf(csrf -> csrf.ignoringRequestMatchers(
                        "/stockData/subscribe/symbol",
                        "/stockData/unsubscribe/{symbol}",
                        "/stockData/subscribe/symbol/{symbolname}")); // Disable CSRF for specific POST
It disables CSRF protection for certain endpoints.
CSRF is a type of attack where a malicious actor tricks a logged-in user into performing actions on a web application without their consent (e.g., submitting a form or making an API call).
Why CSRF Protection? CSRF protection ensures that requests are made intentionally by the user (often by requiring a CSRF token to be included in requests).
If these endpoints are used by a frontend single-page application (SPA) that authenticates using OAuth2 or JWT, disabling CSRF might be acceptable.
*/