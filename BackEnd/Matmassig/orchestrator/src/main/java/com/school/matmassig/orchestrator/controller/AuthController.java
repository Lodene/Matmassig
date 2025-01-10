package com.school.matmassig.orchestrator.controller;

import com.school.matmassig.orchestrator.model.LoginRequest;
import com.school.matmassig.orchestrator.model.LoginResponse;
import com.school.matmassig.orchestrator.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request.getName(), request.getPassword());
            return ResponseEntity.ok(response); // Retourne le token, userId et les rôles
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Invalid username")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username invalid");
            } else if (e.getMessage().equals("Invalid password")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password invalid");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
