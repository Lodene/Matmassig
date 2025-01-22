package com.school.matmassig.recommandationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.recommandationservice.model.RecommendationRequest;
import com.school.matmassig.recommandationservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.school.matmassig.recommandationservice.configuration.RabbitMQConfig;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecommendationListener {

    private final RecommendationService recommendationService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleMessage(String message) {
        try {
            RecommendationRequest request = objectMapper.readValue(message, RecommendationRequest.class);
            log.info("Received recommendation request: {}", request);
            recommendationService.sendToAI(request);
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
        }
    }
}
