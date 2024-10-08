package com.billycychan.acmepark.gate_access_service.adapters;

import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.gate_access_service.ports.outgoing.AccessEventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GateAccessPublisher implements AccessEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(TransponderAccessRequest request) {
        log.info("GateAccessPublisher publish to request.queue result, {}", request);
        rabbitTemplate.convertAndSend(
                "parking.exchange",
                "permit.validated.request",
                request);
    }
}
