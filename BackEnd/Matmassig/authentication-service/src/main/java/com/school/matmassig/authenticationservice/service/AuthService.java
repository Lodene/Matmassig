package com.school.matmassig.authenticationservice.service;

import com.school.matmassig.authenticationservice.model.User;
import com.school.matmassig.authenticationservice.repository.UserRepository;
import com.school.matmassig.authenticationservice.util.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public String login(String name, String password) {
        // Vérifie si l'utilisateur existe
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Invalid username")); // Message pour utilisateur inexistant

        // Vérifie si le mot de passe est correct
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password"); // Message pour mot de passe incorrect
        }

        // Génère un token JWT si tout est correct
        return jwtUtils.generateToken(user);
    }

    public void signup(String name, String email, String password) {
        if (userRepository.existsByName(name) || userRepository.existsByEmail(email)) {
            throw new RuntimeException("Name or email already exists");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }
}
