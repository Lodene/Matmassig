package com.school.matmassig.orchestrator.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "app-exchange";
    public static final String RECIPE_QUEUE = "recipe-queue";
    public static final String REVIEW_QUEUE = "review-queue";

    public static final String ITEM_QUEUE = "item-queue";

    public static final String RECOMMANDATION_QUEUE = "recommandation-queue";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue recipeQueue() {
        return new Queue(RECIPE_QUEUE, true);
    }

    @Bean
    public Queue reviewQueue() {
        return new Queue(REVIEW_QUEUE, true);
    }

    @Bean
    public Queue itemQueue() {
        return new Queue(ITEM_QUEUE, true);
    }

    @Bean
    public Queue recommandationQueue() { return new Queue(RECOMMANDATION_QUEUE, true); }

    @Bean
    public Binding recipeBinding(Queue recipeQueue, TopicExchange exchange) {
        return BindingBuilder.bind(recipeQueue).to(exchange).with("recipe.#");
    }

    @Bean
    public Binding reviewBinding(Queue reviewQueue, TopicExchange exchange) {
        return BindingBuilder.bind(reviewQueue).to(exchange).with("review.#");
    }

    @Bean
    public Binding itemBinding(Queue itemQueue, TopicExchange exchange) {
        return BindingBuilder.bind(itemQueue).to(exchange).with("item.#");
    }

    @Bean
    public Binding recommandationBinding(Queue recommandationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(recommandationQueue).to(exchange).with("recommandation.#");
    }
}
