package com.school.matmassig.recipeservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class RabbitMQConfiguration implements RabbitListenerConfigurer {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("app-exchange");
    }

    @Bean
    public Queue recipeProcessingQueue() {
        return new Queue("recipe-queue", true);
    }

    @Bean
    public Queue esbNotificationsQueue() {
        return new Queue("esb-queue", true);
    }

    @Bean
    public Binding recipeProcessingBinding(Queue recipeProcessingQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(recipeProcessingQueue).to(topicExchange).with("recipe.processing");
    }

    @Bean
    public Binding esbNotificationsBinding(Queue esbNotificationsQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(esbNotificationsQueue).to(topicExchange).with("esb.notifications");
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
    }
}
