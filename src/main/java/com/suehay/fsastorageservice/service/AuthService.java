package com.suehay.fsastorageservice.service;

import com.suehay.fsastorageservice.model.request.AuthenticationRequest;
import com.suehay.fsastorageservice.model.response.AuthenticationResponse;
import com.suehay.fsastorageservice.model.response.GenericResponse;

public interface AuthService {
    GenericResponse<AuthenticationResponse> login(AuthenticationRequest authenticationRequest);

    GenericResponse<AuthenticationResponse> register(AuthenticationRequest authenticationRequest);

    String logout(String token);

    String refreshToken(String token);
}