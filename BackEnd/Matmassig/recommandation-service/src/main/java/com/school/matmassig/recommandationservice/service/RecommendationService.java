package com.school.matmassig.recommandationservice.service;

import com.school.matmassig.recommandationservice.model.RecommendationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RecommendationService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ai.api.url:http://api.example.com/recommendation}")
    private String aiApiUrl;

    public void sendToAI(RecommendationRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RecommendationRequest> entity = new HttpEntity<>(request, headers);

            restTemplate.postForEntity(aiApiUrl, entity, String.class);
            log.info("Successfully sent recommendation request to AI API: {}", request);
        } catch (Exception e) {
            log.error("Failed to send recommendation request to AI API: {}", request, e);
        }
    }
}
