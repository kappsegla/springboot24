package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;

import static org.springframework.security.oauth2.core.authorization.OAuth2AuthorizationManagers.hasAnyScope;
import static org.springframework.security.oauth2.core.authorization.OAuth2AuthorizationManagers.hasScope;

@Configuration
@EnableRetry
public class SecurityConfig {


    @Bean
    @Order(2)
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/login", "/logout", "/error**").permitAll()
                        .requestMatchers("/web/create").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    @Order(1)
    SecurityFilterChain api(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                /*
                always – A session will always be created if one doesn’t already exist.
                ifRequired – A session will be created only if required (default).
                never – The framework will never create a session itself, but it will use one if it already exists.
                stateless – No session will be created or used by Spring Security.
                 */
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.GET, "/api/cats/**").access(hasAnyScope("read", "write", "cats:read"))
                        .requestMatchers(HttpMethod.POST, "/api/cats/**").access(hasScope("write"))
                        .requestMatchers(HttpMethod.PUT, "/api/cats/**").access(hasScope("write"))
                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer((oauth2) ->
                        oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    static RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER\n" +
                               "ROLE_USER > ROLE_GUEST");
        return hierarchy;
    }

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }

}
