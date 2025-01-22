package com.school.matmassig.reviewservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("app-exchange");
    }

    @Bean
    public Queue reviewQueue() {
        return new Queue("review-queue", true);
    }

    @Bean
    public Queue esbNotificationsQueue() {
        return new Queue("esb-queue", true); // ESB notification queue
    }

    @Bean
    public Binding esbNotificationsBinding(Queue esbNotificationsQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(esbNotificationsQueue).to(topicExchange).with("esb.notifications");
    }

    @Bean
    public Binding reviewCreateBinding(Queue reviewQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(reviewQueue).to(topicExchange).with("review.create");
    }

    @Bean
    public Binding reviewDeleteBinding(Queue reviewQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(reviewQueue).to(topicExchange).with("review.delete");
    }

    @Bean
    public Binding reviewUpdateBinding(Queue reviewQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(reviewQueue).to(topicExchange).with("review.update");
    }

    @Bean
    public Binding reviewGetUserBinding(Queue reviewQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(reviewQueue).to(topicExchange).with("review.getbyuser");
    }

    @Bean
    public Binding reviewGetRecipeBinding(Queue reviewQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(reviewQueue).to(topicExchange).with("review.getbyrecipe");
    }
}
