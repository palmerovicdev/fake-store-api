package com.suehay.fsastorageservice.service;

import com.suehay.fsastorageservice.config.JwtService;
import com.suehay.fsastorageservice.model.entity.User;
import com.suehay.fsastorageservice.model.enums.Role;
import com.suehay.fsastorageservice.model.request.AuthenticationRequest;
import com.suehay.fsastorageservice.model.response.AuthenticationResponse;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import com.suehay.fsastorageservice.repository.UserRepository;
import com.suehay.fsastorageservice.util.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger;

    @Override
    public GenericResponse<AuthenticationResponse> login(AuthenticationRequest authenticationRequest) {
        var FUNCTION_CONTEXT = "login";

        logger.info(FUNCTION_CONTEXT, "Starting...");
        var passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.username(), authenticationRequest.password());
        authenticationManager.authenticate(passwordAuthenticationToken);

        var user = userRepository.findByUsername(authenticationRequest.username());
        logger.info(FUNCTION_CONTEXT, "User found: " + user);
        return user.map(value -> new GenericResponse<>(null, "Login successful", "200", AuthenticationResponse.builder()
                                                                                                              .token(jwtService.generateToken(value, generateExtraClaims(value)))
                                                                                                              .username(value.getUsername())
                                                                                                              .role(value.getRole().name())
                                                                                                              .build())).orElseGet(() -> new GenericResponse<>("User not found", "The user with the given username was not found", "404", null));

    }

    private Map<String, Object> generateExtraClaims(User user) {
        return new HashMap<>() {{
            put("role", user.getRole().name());
            put("name", user.getUsername());
        }};
    }

    @Override
    public GenericResponse<AuthenticationResponse> register(AuthenticationRequest authenticationRequest) {
        var FUNCTION_CONTEXT = "register";
        var user = new User();
        user.setUsername(authenticationRequest.username());
        user.setPassword(passwordEncoder.encode(authenticationRequest.password()));
        user.setRole(Role.USER);

        logger.info(FUNCTION_CONTEXT, "Saving user...");
        userRepository.save(user);
        logger.info(FUNCTION_CONTEXT, "User saved: " + user);

        return new GenericResponse<>(null, "User registered successfully", "201", AuthenticationResponse.builder()
                                                                                                        .token(jwtService.generateToken(user, generateExtraClaims(user)))
                                                                                                        .username(user.getUsername())
                                                                                                        .role(user.getRole().name())
                                                                                                        .build());
    }

    @Override
    public GenericResponse<AuthenticationResponse> registerAdmin(AuthenticationRequest authenticationRequest) {
        var FUNCTION_CONTEXT = "registerAdmin";
        var user = new User();
        user.setUsername(authenticationRequest.username());
        user.setPassword(passwordEncoder.encode(authenticationRequest.password()));
        user.setRole(Role.ADMIN);

        logger.info(FUNCTION_CONTEXT, "Saving user...");
        userRepository.save(user);
        logger.info(FUNCTION_CONTEXT, "User saved: " + user);

        return new GenericResponse<>(null, "Register successful", "201", AuthenticationResponse.builder()
                                                                                               .token(jwtService.generateToken(user, generateExtraClaims(user)))
                                                                                               .username(user.getUsername())
                                                                                               .role(user.getRole().name())
                                                                                               .build());
    }
}