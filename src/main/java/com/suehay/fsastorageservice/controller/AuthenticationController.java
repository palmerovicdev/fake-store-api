package com.suehay.fsastorageservice.controller;

import com.suehay.fsastorageservice.model.request.AuthenticationRequest;
import com.suehay.fsastorageservice.model.response.AuthenticationResponse;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import com.suehay.fsastorageservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthService authService;

    @Operation(summary = "Register a user by username and password", description = "Register a user", responses = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "500", description = "Error registering user")
    })
    @PostMapping("/register")
    public ResponseEntity<GenericResponse<AuthenticationResponse>> register(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.register(authenticationRequest));
    }

    @Operation(summary = "Login a user by username and password", description = "Login a user", responses = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Error logging in user")
    })
    @GetMapping("/login")
    public ResponseEntity<GenericResponse<AuthenticationResponse>> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.login(authenticationRequest));
    }
}