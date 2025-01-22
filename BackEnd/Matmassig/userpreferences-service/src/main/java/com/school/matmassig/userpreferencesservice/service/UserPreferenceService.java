package com.school.matmassig.userpreferencesservice.service;

import com.school.matmassig.userpreferencesservice.dto.UserPreferenceDTO;
import com.school.matmassig.userpreferencesservice.entity.UserPreference;

import java.util.List;

public interface UserPreferenceService {
    void createPreference(UserPreferenceDTO preferenceDTO);
    void updatePreference(UserPreferenceDTO preferenceDTO);
    void deletePreference(Long id, Integer userId);
    List<UserPreference> getPreferencesByUserId(Integer userId);
}
