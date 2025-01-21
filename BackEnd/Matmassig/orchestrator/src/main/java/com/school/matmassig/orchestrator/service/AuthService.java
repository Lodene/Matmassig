package com.school.matmassig.orchestrator.service;

import com.school.matmassig.orchestrator.model.LoginRequest;
import com.school.matmassig.orchestrator.model.SignupRequest;
import com.school.matmassig.orchestrator.model.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> login(String email, String password) {
        String url = authServiceUrl + "/login";
        LoginRequest request = new LoginRequest(email, password);

        try {
            System.out.println("Sending POST request to auth-service: " + url);
            System.out.println("Request body: " + request);
            ResponseEntity<LoginResponse> response = restTemplate.postForEntity(url, request, LoginResponse.class);
            System.out.println("Response from auth-service: " + response.getStatusCode() + " - " + response.getBody());
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            System.err.println("Auth-service returned 404: Email not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        } catch (HttpClientErrorException.Unauthorized e) {
            System.err.println("Auth-service returned 401: Incorrect password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        } catch (HttpClientErrorException e) {
            System.err.println("Auth-service returned error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            System.err.println("Unexpected error in orchestrator: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    public ResponseEntity<?> signup(SignupRequest request) {
        return restTemplate.postForEntity(authServiceUrl + "/signup", request, String.class);
    }
}
