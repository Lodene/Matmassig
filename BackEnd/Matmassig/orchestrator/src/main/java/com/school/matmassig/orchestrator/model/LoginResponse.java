package com.school.matmassig.orchestrator.model;

public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
    // Getters and Setters
}