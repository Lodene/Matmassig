package com.school.matmassig.orchestrator.service;

import com.school.matmassig.orchestrator.model.LoginRequest;
import com.school.matmassig.orchestrator.model.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
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

    public LoginResponse login(LoginRequest loginRequest) {
        return restTemplate.postForObject(authServiceUrl, loginRequest, LoginResponse.class);
        //return new LoginResponse("fake-jwt-token", 1L, new String[]{"ROLE_USER"});
    }
}
