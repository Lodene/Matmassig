package com.school.matmassig.notificationservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("app-exchange");
    }

    @Bean
    public Queue esbQueue() {
        return new Queue("esb-queue", true); // Queue durable

    }

    @Bean
    public Binding esbNotificationsBinding(Queue esbNotificationsQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(esbNotificationsQueue).to(topicExchange).with("esb.notifications");
    }

}