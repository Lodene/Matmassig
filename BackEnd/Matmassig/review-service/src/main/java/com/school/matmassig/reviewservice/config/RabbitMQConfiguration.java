package com.school.matmassig.reviewservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.reviewservice.model.Review;
import com.school.matmassig.reviewservice.service.ListenerSerice;

@Configuration
public class RabbitMQConfiguration implements RabbitListenerConfigurer {

    private final ListenerSerice service;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQConfiguration(ListenerSerice service, ObjectMapper objectMapper, RabbitTemplate rabbitTemplate) {
        this.service = service;
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("app-exchange");
    }

    @Bean
    public Queue reviewQueue() {
        return new Queue("review-queue", true); // Durable queue
    }

    @Bean
    public Queue esbQueue() {
        return new Queue("esb-queue", true); // Queue pour l'ESB
    }

    @Bean
    public Binding reviewBinding(Queue reviewQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(reviewQueue).to(topicExchange).with("review.#");
    }

    @Bean
    public Binding esbBinding(Queue esbQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(esbQueue).to(topicExchange).with("esb.#");
    }

    @Bean
    public DefaultMessageHandlerMethodFactory handlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(mappingJackson2MessageConverter());
        return factory;
    }

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(handlerMethodFactory());
    }

    @RabbitListener(queues = "review-queue")
    public void listenAndForward(String message) {
        try {
            // Convertir le message en objet
            Review reviewMessage = objectMapper.readValue(message, Review.class);

            // Sauvegarder dans la base de données
            service.saveReview(reviewMessage);
            System.out.println("review and ingredients are saved!");

            // Envoyer le message à l'ESB
            rabbitTemplate.convertAndSend("esb-queue", message);
            System.out.println("Message forwarded to ESB queue!");
        } catch (Exception e) {
            System.err.println("Failed to process and forward message: " + message);
            e.printStackTrace();
        }
    }

    public void sendToEsbQueue(String message) {
        try {
            rabbitTemplate.convertAndSend("esb-queue", message);
            System.out.println("Message forwarded to ESB queue: " + message);
        } catch (Exception e) {
            System.err.println("Failed to send message to ESB: " + e.getMessage());
        }
    }
}
