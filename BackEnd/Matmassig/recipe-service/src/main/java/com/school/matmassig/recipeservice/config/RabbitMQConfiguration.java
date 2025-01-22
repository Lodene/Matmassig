package com.school.matmassig.recipeservice.config;

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
    public Queue recipeQueue() {
        return new Queue("recipe-queue", true); // Shared queue for all recipe topics
    }

    @Bean
    public Queue recipeIAQueue() {
        return new Queue("recipeIA-queue", true); // Shared queue for all recipe topics
    }

    @Bean
    public Queue esbNotificationsQueue() {
        return new Queue("esb-queue", true); // ESB notification queue
    }

    // Bindings for specific recipe topics
    @Bean
    public Binding recipeCreateBinding(Queue recipeQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(recipeQueue).to(topicExchange).with("recipe.create");
    }

    @Bean
    public Binding recipeDeleteBinding(Queue recipeQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(recipeQueue).to(topicExchange).with("recipe.delete");
    }

    @Bean
    public Binding recipeUpdateBinding(Queue recipeQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(recipeQueue).to(topicExchange).with("recipe.update");
    }

    @Bean
    public Binding recipeGetByUserBinding(Queue recipeQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(recipeQueue).to(topicExchange).with("recipe.getbyuser");
    }

    @Bean
    public Binding recipeGetByRecipeBinding(Queue recipeQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(recipeQueue).to(topicExchange).with("recipe.getbyrecipe");
    }

    @Bean
    public Binding recipeGetAllBinding(Queue recipeQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(recipeQueue).to(topicExchange).with("recipe.getall");
    }

    @Bean
    public Binding esbNotificationsBinding(Queue esbNotificationsQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(esbNotificationsQueue).to(topicExchange).with("esb.notifications");
    }

    // recipeIA
    @Bean
    public Binding recipeIACreateBinding(Queue recipeIAQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(recipeIAQueue).to(topicExchange).with("recipeIA.create");
    }

    @Bean
    public Binding recipeIADeleteBinding(Queue recipeIAQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(recipeIAQueue).to(topicExchange).with("recipeIA.delete");
    }

    @Bean
    public Binding recipeIAUpdateBinding(Queue recipeIAQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(recipeIAQueue).to(topicExchange).with("recipeIA.update");
    }

    @Bean
    public Binding recipeIAGetByUserBinding(Queue recipeIAQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(recipeIAQueue).to(topicExchange).with("recipeIA.getbyuser");
    }
}
