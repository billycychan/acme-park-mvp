package com.billycychan.acmepark.permit_service.adapters;

import com.billycychan.acmepark.permit_service.adapters.action.Actions;
import com.billycychan.acmepark.permit_service.dto.AccessResult;
import com.billycychan.acmepark.permit_service.dto.Message;
import com.billycychan.acmepark.permit_service.ports.outbound.ValidateResponseSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AMQPSender implements ValidateResponseSender {
    private final RabbitTemplate rabbitTemplate;

    public AMQPSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void send(AccessResult result) {
        var message = new Message<AccessResult>();
        message.setAction(Actions.FINISH_TP_VALIDATION);
        message.setPayload(result);
        rabbitTemplate.convertSendAndReceive(
                "permit.validate",
                "response",
                translate(message)
        );
    }

    private String translate(Message<AccessResult> message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
