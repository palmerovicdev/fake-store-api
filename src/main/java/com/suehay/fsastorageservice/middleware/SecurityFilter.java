package com.suehay.fsastorageservice.middleware;

import com.suehay.fsastorageservice.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilter {

    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(authConfig -> {
                    // Auth routes
                    authConfig
                            .requestMatchers(HttpMethod.POST, "/auth/singup").permitAll()
                            .requestMatchers(HttpMethod.GET, "/auth/refresh").permitAll()
                            .requestMatchers(HttpMethod.GET, "/auth/login").permitAll()
                            .requestMatchers("/error").permitAll();

                    // Category routes
                    authConfig
                            .requestMatchers(HttpMethod.POST, "/category/save").hasAnyRole("ADMIN", "USER", "GUEST")
                            .requestMatchers(HttpMethod.GET, "/category/get/**").hasAnyRole("ADMIN", "USER", "GUEST")
                            .requestMatchers(HttpMethod.POST, "/category/findAll").hasAnyRole("ADMIN", "USER", "GUEST")
                            .requestMatchers(HttpMethod.PUT, "/category/update").hasAnyRole("ADMIN", "USER", "GUEST")
                            .requestMatchers(HttpMethod.DELETE, "/category/delete/**").hasAnyRole("ADMIN", "USER", "GUEST");

                    // Product routes
                    authConfig
                            .requestMatchers(HttpMethod.POST, "/product/save").hasAnyRole("ADMIN", "USER", "GUEST")
                            .requestMatchers(HttpMethod.GET, "/product/get/**").hasAnyRole("ADMIN", "USER", "GUEST")
                            .requestMatchers(HttpMethod.PUT, "/product/update").hasAnyRole("ADMIN", "USER", "GUEST")
                            .requestMatchers(HttpMethod.DELETE, "/product/delete/**").hasAnyAuthority();
                    // File routes
                    authConfig
                            .requestMatchers(HttpMethod.POST, "/file/save").hasAnyRole("ADMIN", "USER", "GUEST")
                            .requestMatchers(HttpMethod.GET, "/file/find").hasAnyRole("ADMIN", "USER", "GUEST")
                            .requestMatchers(HttpMethod.POST, "/file/findAllIn").hasAnyRole("ADMIN", "USER", "GUEST")
                            .requestMatchers(HttpMethod.DELETE, "/file/delete").hasAnyRole("ADMIN", "USER", "GUEST¡");

                    authConfig.anyRequest().denyAll();
                })
                ;

        return http.build();
    }
}