package com.school.matmassig.orchestrator.controller;

import com.school.matmassig.orchestrator.model.LoginRequest;
import com.school.matmassig.orchestrator.model.LoginResponse;
import com.school.matmassig.orchestrator.model.SignupRequest;
import com.school.matmassig.orchestrator.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:8100")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println("Received login request for email: " + request.getEmail());
        ResponseEntity<?> response = authService.login(request.getEmail(), request.getPassword());
        System.out.println("Response sent to client: " + response.getStatusCode());
        return response;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        return authService.signup(request);
    }
}
