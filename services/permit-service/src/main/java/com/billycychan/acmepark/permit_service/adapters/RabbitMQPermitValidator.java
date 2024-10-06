package com.billycychan.acmepark.permit_service.adapters;

import com.billycychan.acmepark.permit_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.permit_service.ports.PermitValidationUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQPermitValidator {
    private final PermitValidationUseCase permitValidationUseCase;

    public RabbitMQPermitValidator(PermitValidationUseCase permitValidationUseCase) {
        this.permitValidationUseCase = permitValidationUseCase;
    }

    @RabbitListener(queues = "permit.validate.queue")
    public void validatePermit(String data) {
        TransponderAccessRequest request = translate(data);
        boolean result = permitValidationUseCase.validatePermit(request);
        log.info(result ? "Validated" : "Not Validated") ;
    }

    private TransponderAccessRequest translate(String raw) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(raw, TransponderAccessRequest.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
