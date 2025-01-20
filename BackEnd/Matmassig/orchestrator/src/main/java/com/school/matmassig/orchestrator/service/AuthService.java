package com.school.matmassig.orchestrator.service;

import com.school.matmassig.orchestrator.model.LoginRequest;
import com.school.matmassig.orchestrator.model.SignupRequest;
import com.school.matmassig.orchestrator.model.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {
    private final RestTemplate restTemplate;
    private final String authServiceUrl;

    public AuthService(RestTemplate restTemplate, @Value("${auth.service.url}") String authServiceUrl) {
        this.restTemplate = restTemplate;
        this.authServiceUrl = authServiceUrl;
    }

    public ResponseEntity<?> login(LoginRequest request) {
        return restTemplate.postForEntity(authServiceUrl + "/login", request, LoginResponse.class);
    }

    public ResponseEntity<?> signup(SignupRequest request) {
        return restTemplate.postForEntity(authServiceUrl + "/signup", request, String.class);
    }
}
