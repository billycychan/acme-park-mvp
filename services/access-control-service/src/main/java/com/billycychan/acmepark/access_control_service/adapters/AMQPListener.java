package com.billycychan.acmepark.access_control_service.adapters;

import com.billycychan.acmepark.access_control_service.dto.AccessResult;
import com.billycychan.acmepark.access_control_service.dto.Message;
import com.billycychan.acmepark.access_control_service.dto.TransponderAccess;
import com.billycychan.acmepark.access_control_service.ports.inbound.AccessRequestHandling;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    private final AccessRequestHandling accessRequestHandling;

    public AMQPListener(AccessRequestHandling accessRequestHandling) {
        this.accessRequestHandling = accessRequestHandling;
    }

    @RabbitListener(queues = {"access.request.queue"}, concurrency = "4")
    public void listenAccessRequestQueue(String data) {
        log.info("Received data,{}, ", data);
        var deserializedMessage = translate(data, new TypeReference<Message<JsonNode>>() {});
        if (deserializedMessage.getAction().equals(Actions.ENTER_GATE_TRANSPONDER)) {
            handleEnterGateTransponder(deserializedMessage.getPayload());
        }
    }

    @RabbitListener(queues = {"permit.validate.response.queue"}, concurrency = "4")
    public void listenValidateResponseQueue(String data) {
        log.info("Received data,{}, ", data);
        var deserializedMessage = translate(data, new TypeReference<Message<JsonNode>>() {});
        if (deserializedMessage.getAction().equals(Actions.FINISH_TP_VALIDATION)) {
            handleFinishTpValidation(deserializedMessage.getPayload());
        }
    }

    private void handleEnterGateTransponder(JsonNode payload) {
        try {
            TransponderAccess accessRequest = objectMapper.treeToValue(payload, TransponderAccess.class);
            accessRequestHandling.receiveTransponderEntryRequest(accessRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleFinishTpValidation(JsonNode payload) {
        try {
            AccessResult result = objectMapper.treeToValue(payload, AccessResult.class);
            accessRequestHandling.receiveTransponderValidatedResult(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // Generic translate method for Message<T>
    private <T> Message<T> translate(String raw, TypeReference<Message<T>> typeReference) {
        try {
            return objectMapper.readValue(raw, typeReference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}