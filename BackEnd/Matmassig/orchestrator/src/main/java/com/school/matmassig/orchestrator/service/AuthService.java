package com.school.matmassig.orchestrator.service;

import com.school.matmassig.orchestrator.model.LoginRequest;
import com.school.matmassig.orchestrator.model.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private final String authServiceUrl;

    public AuthService(RestTemplate restTemplate, @Value("${auth.service.url}") String authServiceUrl) {
        this.restTemplate = restTemplate;
        this.authServiceUrl = authServiceUrl;
    }

    public LoginResponse login(String name, String password) {
        String url = "http://authentification-service:8083/api/auth/login";

        try {
            LoginRequest request = new LoginRequest(name, password);
            LoginResponse response = restTemplate.postForObject(url, request, LoginResponse.class);
            return response;
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Invalid username");
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new RuntimeException("Invalid password");
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error during login: " + e.getResponseBodyAsString());
        }
    }
}
