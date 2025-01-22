package com.school.matmassig.inventoryservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("app-exchange");
    }

    @Bean
    public Queue itemQueue() {
        return new Queue("item-queue", true);
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
    public Binding bindingAddItem(Queue itemQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(itemQueue).to(topicExchange).with("item.create");
    }

    @Bean
    public Binding bindingDeleteItem(Queue itemQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(itemQueue).to(topicExchange).with("item.delete");
    }

    @Bean
    public Binding bindingUpdateItem(Queue itemQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(itemQueue).to(topicExchange).with("item.update");
    }

    @Bean
    public Binding bindingGetItems(Queue itemQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(itemQueue).to(topicExchange).with("item.getbyuser");
    }
}