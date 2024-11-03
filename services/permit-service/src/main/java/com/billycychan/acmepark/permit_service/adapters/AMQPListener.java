package com.billycychan.acmepark.permit_service.adapters;

import com.billycychan.acmepark.permit_service.adapters.action.ActionHandlerRegistry;
import com.billycychan.acmepark.permit_service.adapters.action.Actions;
import com.billycychan.acmepark.permit_service.dto.Message;
import com.billycychan.acmepark.permit_service.dto.TransponderAccess;
import com.billycychan.acmepark.permit_service.ports.inbound.PermitValidateHandling;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AMQPListener {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PermitValidateHandling permitValidateHandling;

    public AMQPListener(PermitValidateHandling permitValidateHandling) {
        this.permitValidateHandling = permitValidateHandling;
    }

    @RabbitListener(queues = {"permit.validate.request.queue"}, concurrency = "4")
    public void listenValidateRequesteQueue(String data) {
        log.info("Received data,{}, ", data);
        var deserializedMessage = translate(data, new TypeReference<Message<JsonNode>>() {});
        if (deserializedMessage.getAction().equals(Actions.REQUEST_TP_VALIDATION)) {
            handleRequestTpValidation(deserializedMessage.getPayload());
        }
    }

    private void handleRequestTpValidation(JsonNode payload) {
        try {
            var result = objectMapper.treeToValue(payload, TransponderAccess.class);
            log.info("Handling Permit Validate Request: {}", payload);
            permitValidateHandling.receivePermitValidateRequest(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Generic translate method for Message<T>
    private <T> Message<T> translate(String raw, TypeReference<Message<T>> typeReference) {
        try {
            return objectMapper.readValue(raw, typeReference);
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
}