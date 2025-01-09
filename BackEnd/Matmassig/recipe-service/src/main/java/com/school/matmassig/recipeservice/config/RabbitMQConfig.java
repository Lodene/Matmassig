package com.school.matmassig.recipeservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "app-exchange";
    public static final String RECIPE_QUEUE = "recipe-queue";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue recipeQueue() {
        return new Queue(RECIPE_QUEUE, false);
    }

    @Bean
    public Binding recipeBinding(Queue recipeQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(recipeQueue).to(topicExchange).with("recipe.create");
    }
}
