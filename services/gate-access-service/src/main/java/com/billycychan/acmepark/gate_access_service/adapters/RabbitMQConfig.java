package com.billycychan.acmepark.gate_access_service.adapters;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange permitExchange() {
        return new DirectExchange("permit.exchange");
    }

    @Bean
    public Queue permitValidationQueue() {
        return new Queue("permit.validate.queue");
    }

    @Bean
    public Binding permitValidationBinding(Queue permitValidationQueue, DirectExchange permitExchange) {
        return BindingBuilder.bind(permitValidationQueue)
                .to(permitExchange)
                .with("permit.validate");
    }
}