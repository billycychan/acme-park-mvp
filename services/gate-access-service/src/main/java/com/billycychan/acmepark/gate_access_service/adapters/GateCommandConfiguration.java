package com.billycychan.acmepark.gate_access_service.adapters;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class GateCommandConfiguration {
    @Bean
    public TopicExchange permitValidationExchange() {
        return new TopicExchange("gate.command");
    }

    @Bean
    public Map<String, Queue> gateQueues() {
        Map<String, Queue> queues = new HashMap<>();

        for (int lot = 1; lot <= 5; lot++) {
            for (int gate = 1; gate <= 2; gate++) {
                String queueName = String.format("parking.lot.%d.gate.%d.command", lot, gate);
                queues.put(queueName, new Queue(queueName));
            }
        }

        return queues;
    }
}
