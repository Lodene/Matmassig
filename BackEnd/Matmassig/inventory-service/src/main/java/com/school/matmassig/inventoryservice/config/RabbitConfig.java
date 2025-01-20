package com.school.matmassig.inventoryservice.config;

import org.springframework.amqp.core.Queue;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "esb-queue";
    public static final String EXCHANGE_NAME = "item-esb";

    public static final String ROUTING_KEY_ADD = "item.create";
    public static final String ROUTING_KEY_DELETE = "item.delete";
    public static final String ROUTING_KEY_UPDATE = "item.update";
    public static final String ROUTING_KEY_GET = "item.getbyuser";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingAddItem(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_ADD);
    }

    @Bean
    public Binding bindingDeleteItem(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_DELETE);
    }

    @Bean
    public Binding bindingUpdateItem(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_UPDATE);
    }

    @Bean
    public Binding bindingGetItems(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_GET);
    }
}