package com.billycychan.acmepark.gate_access_service.adapters;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class RabbitMQConfig {
    @Value("${app.custom.messaging.outbound-exchange-topic}")
    private String exchange;

    @Bean
    public TopicExchange outbound() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue myQueue() {
        return new Queue("gate.access.permit.validate");
    }

    @Bean
    public Binding binding(Queue myQueue, TopicExchange outbound) {
        return BindingBuilder.bind(myQueue).to(outbound).with("/");
    }
}
