package com.school.matmassig.inventoryservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "app-exchange";
    public static final String QUEUE_NAME_ITEM = "esb-queue";
    public static final String QUEUE_NAME_ESB = "item-queue";

    public static final String ROUTING_KEY_ADD = "item.create";
    public static final String ROUTING_KEY_DELETE = "item.delete";
    public static final String ROUTING_KEY_UPDATE = "item.update";
    public static final String ROUTING_KEY_GET = "item.getbyuser";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue itemQueue() {
        return new Queue(QUEUE_NAME_ITEM, true);
    }

    @Bean
    public Queue esbNotificationsQueue() {
        return new Queue(QUEUE_NAME_ESB, true);
    }

    @Bean
    public Binding esbNotificationsBinding(Queue esbNotificationsQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(esbNotificationsQueue).to(topicExchange).with("esb.notifications");
    }

    @Bean
    public Binding bindingAddItem(Queue itemQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(itemQueue).to(topicExchange).with(ROUTING_KEY_ADD);
    }

    @Bean
    public Binding bindingDeleteItem(Queue itemQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(itemQueue).to(topicExchange).with(ROUTING_KEY_DELETE);
    }

    @Bean
    public Binding bindingUpdateItem(Queue itemQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(itemQueue).to(topicExchange).with(ROUTING_KEY_UPDATE);
    }

    @Bean
    public Binding bindingGetItems(Queue itemQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(itemQueue).to(topicExchange).with(ROUTING_KEY_GET);
    }
}