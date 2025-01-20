package com.school.matmassig.inventoryservice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.school.matmassig.inventoryservice.config.RabbitConfig;

@Service
public class InventoryProducer {

    private final RabbitTemplate rabbitTemplate;

    public InventoryProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Envoie un message à l'échange `inventory-esb` via la queue `esb-queue`.
     *
     * @param message Le message à envoyer.
     */
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.QUEUE_NAME, message);
        System.out.println("Message envoyé à la queue `esb-queue` : " + message);
    }
}