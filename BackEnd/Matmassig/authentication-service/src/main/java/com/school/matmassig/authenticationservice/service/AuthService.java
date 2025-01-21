package com.school.matmassig.authenticationservice.service;

import com.school.matmassig.authenticationservice.model.User;
import com.school.matmassig.authenticationservice.repository.UserRepository;
import com.school.matmassig.authenticationservice.util.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public String login(String email, String password) {
        System.out.println("Login request received for email: " + email);

        // Vérification de l'email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.err.println("Email not found: " + email);
                    return new IllegalArgumentException("Invalid email");
                });

        System.out.println("User found: " + user);

        // Vérification du mot de passe
        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.err.println("Password mismatch for email: " + email);
            throw new IllegalArgumentException("Invalid password");
        }

        System.out.println("Password verified for email: " + email);

        // Génération du token
        String token = jwtUtils.generateToken(user);
        System.out.println("Generated JWT token for email: " + email);
        return token;
    }

    public void signup(String email, String name, String password) {
        if (userRepository.existsByEmail(email)) {
            System.err.println("Email already in use: " + email);
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        System.out.println("Attempting to save user: " + user);
        userRepository.save(user);
        System.out.println("User successfully saved: " + user);
    }

}
