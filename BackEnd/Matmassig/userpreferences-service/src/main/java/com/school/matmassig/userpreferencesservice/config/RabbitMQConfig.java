package com.school.matmassig.userpreferencesservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "app-exchange";
    public static final String QUEUE_NAME = "preference-queue";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
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
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Binding bindingCreate(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("preference.create");
    }

    @Bean
    public Binding bindingUpdate(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("preference.update");
    }

    @Bean
    public Binding bindingDelete(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("preference.delete");
    }

    @Bean
    public Binding bindingGetByUser(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("preference.getbyuser");
    }
}
