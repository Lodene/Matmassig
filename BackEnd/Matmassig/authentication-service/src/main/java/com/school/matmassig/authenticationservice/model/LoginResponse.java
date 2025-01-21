package com.school.matmassig.authenticationservice.model;

public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }
}

