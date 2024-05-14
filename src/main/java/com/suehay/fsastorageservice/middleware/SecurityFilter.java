package com.suehay.fsastorageservice.middleware;

import com.suehay.fsastorageservice.config.JwtAuthenticationFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilter {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authConfig -> {
                    // Auth routes
                    authConfig
                            .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                            .requestMatchers(HttpMethod.POST, "/auth/register/admin").hasAnyAuthority("CREATE_ADMIN")
                            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                            .requestMatchers("/error").permitAll();

                    // Category routes
                    authConfig
                            .requestMatchers(HttpMethod.POST, "/category/save").hasAnyAuthority("WRITE")
                            .requestMatchers(HttpMethod.GET, "/category/get/**").hasAnyAuthority("READ")
                            .requestMatchers(HttpMethod.GET, "/category/findAll").hasAnyAuthority("READ")
                            .requestMatchers(HttpMethod.PUT, "/category/update").hasAnyAuthority("UPDATE")
                            .requestMatchers(HttpMethod.DELETE, "/category/delete/**").hasAnyAuthority("DELETE");

                    // Product routes
                    authConfig
                            .requestMatchers(HttpMethod.POST, "/product/save").hasAnyAuthority("WRITE")
                            .requestMatchers(HttpMethod.GET, "/product/get/**").hasAnyAuthority("READ")
                            .requestMatchers(HttpMethod.PUT, "/product/update").hasAnyAuthority("UPDATE")
                            .requestMatchers(HttpMethod.DELETE, "/product/delete/**").hasAnyAuthority("DELETE");
                    // File routes
                    authConfig
                            .requestMatchers(HttpMethod.POST, "/file/save").hasAnyAuthority("WRITE")
                            .requestMatchers(HttpMethod.GET, "/file/find").hasAnyAuthority("READ")
                            .requestMatchers(HttpMethod.GET, "/file/findAllIn").hasAnyAuthority("READ")
                            .requestMatchers(HttpMethod.DELETE, "/file/delete").hasAnyAuthority("DELETE");
                    // Swagger routes
                    authConfig
                            .requestMatchers("/swagger-ui/**").permitAll()
                            .requestMatchers("/v3/api-docs/**").permitAll()
                            .requestMatchers("/swagger-ui.html").permitAll();
                    authConfig.anyRequest().denyAll();
                })
        ;

        return http.build();
    }
}