package com.billycychan.acmepark.access_control_service.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue permitValidateRequest() {
        return new Queue("permit.validate.request.queue", true);
    }

    @Bean
    public Queue permitValidateResponse() {
        return new Queue("permit.validate.response.queue", true);
    }

    @Bean
    public Queue accessRequestQueue() {
        return new Queue("access.request.queue", true);
    }

    @Bean
    public Queue gateCommandQueue() {
        return new Queue("gate.command.queue", true);
    }

    @Bean
    public TopicExchange permitValidate() {
        return new TopicExchange("permit.validate");
    }

    @Bean
    public DirectExchange accessExchange() {
        return new DirectExchange("access");
    }

    @Bean
    public DirectExchange gateCommand() {
        return new DirectExchange("gate.command");
    }

    @Bean
    public Binding permitValidateRequestBinding(Queue permitValidateRequest, TopicExchange permitValidate) {
        return BindingBuilder.bind(permitValidateRequest)
                .to(permitValidate)
                .with("request");
    }

    @Bean
    public Binding permitValidateResponseBinding(Queue permitValidateResponse, TopicExchange permitValidate) {
        return BindingBuilder.bind(permitValidateResponse)
                .to(permitValidate)
                .with("response");
    }

    @Bean
    public Binding transponderAccessRequestBinding(Queue accessRequestQueue, DirectExchange accessExchange) {
        return BindingBuilder.bind(accessRequestQueue)
                .to(accessExchange)
                .with("request");
    }

    @Bean
    public Binding gateCommandBinding(Queue gateCommandQueue, DirectExchange gateCommand) {
        return BindingBuilder.bind(gateCommandQueue)
                .to(gateCommand)
                .with("command");
    }
}