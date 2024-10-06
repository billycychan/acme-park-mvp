package com.billycychan.acmepark.gate_access_service.adapters;

import com.billycychan.acmepark.gate_access_service.dto.AccessRequest;
import com.billycychan.acmepark.gate_access_service.ports.AccessRequestSender;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GateControlSender implements AccessRequestSender {

    @Value("${app.custom.messaging.outbound-exchange-topic}")
    private String exchange;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public GateControlSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @Override
    public void send(AccessRequest accessRequest) {
        log.info("Sending message to {}: {}", exchange, accessRequest);
        rabbitTemplate.convertAndSend(exchange, "*", translate(accessRequest));
    }

    private String translate(AccessRequest accessRequest) {
        ObjectMapper mapper= new ObjectMapper();
        try {
            return mapper.writeValueAsString(accessRequest);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
