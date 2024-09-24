package com.uade.tpo.ecommerce.ecommerce.controller.config;


import com.uade.tpo.ecommerce.ecommerce.repository.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/users/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST,"/api/v1/products").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST,"/api/v1/products/featured/*").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/products/featured/*").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/products/*").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH,"/api/v1/products/*/*").hasAnyAuthority(Role.ADMIN.name())
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
