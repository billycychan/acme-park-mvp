package com.billycychan.acmepark.permit_service.adapters;

import com.billycychan.acmepark.permit_service.dto.AccessResult;
import com.billycychan.acmepark.permit_service.ports.outbound.ResponseSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AMQPSender implements ResponseSender  {
    private final RabbitTemplate rabbitTemplate;

    public AMQPSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishAccessResult(AccessResult result) {
        rabbitTemplate.convertSendAndReceive(
                "parking.exchange",
                "permit.validated.response",
                result
        );
    }
}
