package com.school.matmassig.userpreferencesservice.repository;

import com.school.matmassig.userpreferencesservice.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
    List<UserPreference> findByUserId(Integer userId);
}
