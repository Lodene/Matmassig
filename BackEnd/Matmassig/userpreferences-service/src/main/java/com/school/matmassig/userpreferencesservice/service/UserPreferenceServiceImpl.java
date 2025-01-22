package com.school.matmassig.userpreferencesservice.service;

import com.school.matmassig.userpreferencesservice.dto.UserPreferenceDTO;
import com.school.matmassig.userpreferencesservice.entity.UserPreference;
import com.school.matmassig.userpreferencesservice.repository.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserPreferenceServiceImpl implements UserPreferenceService {

    @Autowired
    private UserPreferenceRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(UserPreferenceServiceImpl.class);

    @Override
    public void createPreference(UserPreferenceDTO preferenceDTO) {
        UserPreference preference = new UserPreference();
        preference.setUserId(preferenceDTO.getUserId());
        preference.setGenre(preferenceDTO.getGenre());
        preference.setValue(preferenceDTO.getValue());
        repository.save(preference);
    }

    @Override
    public void updatePreference(UserPreferenceDTO preferenceDTO) {
        UserPreference preference = repository.findById(preferenceDTO.getId()).orElseThrow();
        preference.setGenre(preferenceDTO.getGenre());
        preference.setValue(preferenceDTO.getValue());
        preference.setUpdatedAt(java.time.LocalDateTime.now());
        repository.save(preference);
    }

    @Override
    public void deletePreference(Long id, Integer userId) {
        repository.deleteById(id);
    }

    @Override
    public List<UserPreference> getPreferencesByUserId(Integer userId) {
        List<UserPreference> preferences = repository.findByUserId(userId);

        // Log all preferences retrieved
        if (preferences.isEmpty()) {
            logger.info("No preferences found for userId: {}", userId);
        } else {
            logger.info("Preferences retrieved for userId {}: {}", userId, preferences);
        }

        return preferences;
    }
}
