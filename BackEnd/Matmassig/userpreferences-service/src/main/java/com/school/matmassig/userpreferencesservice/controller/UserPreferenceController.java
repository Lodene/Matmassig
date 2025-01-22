package com.school.matmassig.userpreferencesservice.controller;

import com.school.matmassig.userpreferencesservice.entity.UserPreference;
import com.school.matmassig.userpreferencesservice.service.UserPreferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preference")
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    public UserPreferenceController(UserPreferenceService userPreferenceService) {
        this.userPreferenceService = userPreferenceService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserPreference>> getPreferencesByUserId(@PathVariable Integer userId) {
        List<UserPreference> preferences = userPreferenceService.getPreferencesByUserId(userId);

        // Retourne une réponse HTTP 200 avec la liste des préférences
        return ResponseEntity.ok(preferences);
    }
}
