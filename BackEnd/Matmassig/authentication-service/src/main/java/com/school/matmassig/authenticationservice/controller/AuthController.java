package com.school.matmassig.authenticationservice.controller;

import com.school.matmassig.authenticationservice.model.User;
import com.school.matmassig.authenticationservice.service.AuthService;
import com.school.matmassig.authenticationservice.model.LoginRequest;
import com.school.matmassig.authenticationservice.model.SignupRequest;
import com.school.matmassig.authenticationservice.model.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Invalid email")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
            } else if (e.getMessage().equals("Invalid password")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            authService.signup(request.getEmail(), request.getName(), request.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Email already in use")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
