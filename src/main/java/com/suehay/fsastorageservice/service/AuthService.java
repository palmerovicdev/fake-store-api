package com.suehay.fsastorageservice.service;

public interface AuthService {
    String login(String username, String password);

    String register(String username, String password);

    String logout(String token);

    String refreshToken(String token);
}