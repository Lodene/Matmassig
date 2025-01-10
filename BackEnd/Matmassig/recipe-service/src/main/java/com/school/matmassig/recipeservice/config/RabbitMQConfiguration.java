package com.school.matmassig.recipeservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.recipeservice.model.RecipeMessage;
import com.school.matmassig.recipeservice.service.ListenerSerice;

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
    public Queue recipeQueue() {
        return new Queue("recipe-queue", true); // Durable queue
    }

    @Bean
    public Binding recipeBinding(Queue recipeQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(recipeQueue).to(topicExchange).with("recipe.#");
    }

    @Bean
    public Queue esbQueue() {
        return new Queue("esb-queue", true); // Durable queue
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

    @RabbitListener(queues = "recipe-queue")
    public void listenAndForward(String message) {
        try {
            // Convertir le message en objet
            RecipeMessage recipeMessage = objectMapper.readValue(message, RecipeMessage.class);

            // Sauvegarder dans la base de données
            service.saveRecipeAndIngredients(recipeMessage.getRecipe(), recipeMessage.getIngredients());
            System.out.println("Recipe and ingredients are saved!");

            // Envoyer le message à l'ESB
            rabbitTemplate.convertAndSend("app-exchange", "esb.recipe", message);
            System.out.println("Message forwarded to ESB queue!");
        } catch (Exception e) {
            System.err.println("Failed to process and forward message: " + message);
            e.printStackTrace();
        }
    }
}
