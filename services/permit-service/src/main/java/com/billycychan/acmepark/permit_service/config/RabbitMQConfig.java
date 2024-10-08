package com.billycychan.acmepark.permit_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue requestQueue() {
        return new Queue("permit.validated.request.queue", true);
    }

    @Bean
    public Queue responseQueue() {
        return new Queue("permit.validated.response.queue", true);
    }

    @Bean
    public TopicExchange parkingExchange() {
        return new TopicExchange("parking.exchange");
    }

    @Bean
    public Binding permitValidatedBinding1(Queue requestQueue, TopicExchange parkingExchange) {
        return BindingBuilder.bind(requestQueue)
                .to(parkingExchange)
                .with("permit.validated.request");
    }

    @Bean
    public Binding permitValidatedBinding2(Queue responseQueue, TopicExchange parkingExchange) {
        return BindingBuilder.bind(responseQueue)
                .to(parkingExchange)
                .with("permit.validated.response");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}