package com.billycychan.acmepark.access_control_service.adapters;

import com.billycychan.acmepark.access_control_service.adapters.action.Actions;
import com.billycychan.acmepark.access_control_service.dto.Message;
import com.billycychan.acmepark.access_control_service.dto.TransponderAccess;
import com.billycychan.acmepark.access_control_service.ports.outbound.PermitValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AMQPSender implements PermitValidation {

    @Autowired
    final ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public AMQPSender(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void send(TransponderAccess access) {
        var message = new Message<TransponderAccess>();
        message.setAction(Actions.REQUEST_TP_VALIDATION);
        message.setPayload(access);
        rabbitTemplate.convertSendAndReceive("permit.validate", "request", translate(message));
    }

    private String translate(Message<TransponderAccess> message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
