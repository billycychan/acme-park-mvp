package com.billycychan.acmepark.access_control_service.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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
    public Queue transponderAccessRequest() {
        return new Queue("transponder.access.request.queue", true);
    }

    @Bean
    public TopicExchange permitValidate() {
        return new TopicExchange("permit.validate");
    }

    @Bean
    public DirectExchange transponderAccess() {
        return new DirectExchange("transponder.access");
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
    public Binding transponderAccessRequestBinding(Queue transponderAccessRequest, DirectExchange transponderAccess) {
        return BindingBuilder.bind(transponderAccessRequest)
                .to(transponderAccess)
                .with("request");
    }

//    @Bean
//    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
//        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
//        return rabbitTemplate;
//    }

//    @Bean
//    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
}