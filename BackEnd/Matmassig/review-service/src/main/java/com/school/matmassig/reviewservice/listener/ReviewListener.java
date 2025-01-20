package com.school.matmassig.reviewservice.listener;

import com.school.matmassig.reviewservice.service.RabbitMQMessageProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class ReviewListener {

    private final RabbitMQMessageProcessor messageProcessor;

    public ReviewListener(RabbitMQMessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @RabbitListener(queues = "review-queue")
    public void listenAndProcess(String message, @Header("amqp_receivedRoutingKey") String routingKey) {
        messageProcessor.processMessage(message, routingKey);
    }
}
