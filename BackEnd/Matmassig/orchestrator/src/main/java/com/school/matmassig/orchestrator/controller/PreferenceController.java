package com.school.matmassig.orchestrator.controller;

import com.school.matmassig.orchestrator.model.UserPreference;
import com.school.matmassig.orchestrator.service.RabbitMQPublisherService;
import com.school.matmassig.orchestrator.util.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.school.matmassig.orchestrator.config.RabbitMQConfig.EXCHANGE_NAME;

@RestController
@RequestMapping("/api/orchestrator/preferences")
public class PreferenceController {

    private final RabbitMQPublisherService publisherService;

    private final JwtUtils jwtUtils;


    public PreferenceController(RabbitMQPublisherService publisherService, JwtUtils jwtUtils) {
        this.publisherService = publisherService;
        this.jwtUtils = jwtUtils;
    }

    // Add UserPreference
    @PostMapping("/add")
    public ResponseEntity<String> addPreference(@RequestBody UserPreference userPreference, @RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromToken(authHeader);
        Integer userId = extractUserIdFromToken(authHeader);
        userPreference.setEmail(email);
        userPreference.setUserId(userId);
        publisherService.publishMessage(EXCHANGE_NAME, "preference.create", userPreference);
        return ResponseEntity.ok("UserPreference added and sent to RabbitMQ");
    }

    // Update UserPreference
    @PutMapping("/update")
    public ResponseEntity<String> updatePreference(@RequestBody UserPreference userPreference, @RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromToken(authHeader);
        Integer userId = extractUserIdFromToken(authHeader);
        userPreference.setEmail(email);
        userPreference.setUserId(userId);
        publisherService.publishMessage(EXCHANGE_NAME, "preference.update", userPreference);
        return ResponseEntity.ok("UserPreference updated and sent to RabbitMQ");
    }

    // Delete UserPreference
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePreference(@RequestBody UserPreference userPreference, @RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromToken(authHeader);
        Integer userId = extractUserIdFromToken(authHeader);
        userPreference.setEmail(email);
        userPreference.setUserId(userId);
        publisherService.publishMessage(EXCHANGE_NAME, "preference.delete", userPreference);
        return ResponseEntity.ok("UserPreference deleted and sent to RabbitMQ");
    }

    // Get Preferences by User
    @GetMapping("/getbyuser/{userId}")
    public ResponseEntity<String> getPreferencesByUser(@PathVariable int userId, @RequestHeader("Authorization") String authHeader) {
        Map<String, Object> payload = new HashMap<>();
        String email = extractEmailFromToken(authHeader);
        payload.put("userId", userId);
        publisherService.publishMessageWithAdditionalData(EXCHANGE_NAME, "preference.getbyuser", payload, email);
        return ResponseEntity.ok("Get UserPreference request sent to RabbitMQ");
    }
    private String extractEmailFromToken(String authHeader) {
        String token = authHeader.substring(7); // Suppression du préfixe "Bearer "
        return jwtUtils.getEmailFromToken(token);
    }

    private Integer extractUserIdFromToken(String authHeader) {
        String token = authHeader.substring(7); // Suppression du préfixe "Bearer "
        return jwtUtils.getUserIdFromToken(token);
    }
}
