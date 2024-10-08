package com.billycychan.acmepark.gate_access_service.adapters;

import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.gate_access_service.ports.outbound.RequestSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GatePublisher implements RequestSender {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(TransponderAccessRequest request) {
        rabbitTemplate.convertAndSend(
                "parking.exchange",
                "permit.validated.request",
                request);
    }
}
