package com.billycychan.acmepark.gate_access_service.adapters;

import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.gate_access_service.ports.PermitValidationPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PermitValidationAdapter implements PermitValidationPort {
    @Autowired
    private final RabbitTemplate rabbitTemplate;

    public PermitValidationAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void isPermitValid(TransponderAccessRequest transponderAccessRequest) {
        String message = translate(transponderAccessRequest);
        log.info("sending isPermitValid message {}", message);
        rabbitTemplate.convertSendAndReceive(
                "permit.exchange",
                "permit.validate",
                message);
    }

    private String translate(TransponderAccessRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}