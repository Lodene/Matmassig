package com.school.matmassig.orchestrator.model;

public class LoginResponse {
    private String token;
    private Long userId;
    private String[] roles;

    public LoginResponse(String token, Long userId, String[] roles) {
        this.token = token;
        this.userId = userId;
        this.roles = roles;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
