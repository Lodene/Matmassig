package com.school.matmassig.inventoryservice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.school.matmassig.inventoryservice.config.RabbitConfig;
import com.school.matmassig.inventoryservice.service.MessageHandlerService;

@Component
public class InventoryListener {

    private final MessageHandlerService messageHandlerService;

    public InventoryListener(MessageHandlerService messageHandlerService) {
        this.messageHandlerService = messageHandlerService;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void listen(String message, @Header("amqp_receivedRoutingKey") String routingKey) {
        messageHandlerService.processMessage(message, routingKey);
    }
}