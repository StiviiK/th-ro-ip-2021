package com.example.backend.models;

import lombok.Getter;

public class AuthenticationResponse {
    @Getter
    private final String username;

    @Getter
    private final String token;

    public AuthenticationResponse(String username, String jwt) {
        this.username = username;
        this.token = jwt;
    }
}
