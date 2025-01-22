package com.school.matmassig.recommandationservice.model;

import lombok.Data;

@Data
public class RecommendationRequest {
    private int userId;
    private String email;
}
