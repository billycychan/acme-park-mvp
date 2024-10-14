package com.billycychan.acmepark.permit_service.adapters;

import com.billycychan.acmepark.permit_service.adapters.action.ActionHandlerRegistry;
import com.billycychan.acmepark.permit_service.dto.Message;
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
    private final ActionHandlerRegistry registry;

    public AMQPListener(ActionHandlerRegistry registry) {
        this.registry = registry;
    }

    @RabbitListener(queues = {"permit.validate.request.queue"}, concurrency = "4")
    public void listen(String data) {
        var deserializedMessage = translate(data, new TypeReference<Message<JsonNode>>() {});
        String action = deserializedMessage.getAction();
        JsonNode payload = deserializedMessage.getPayload();
        var handler = registry.getHandler(action);
        if (handler != null) {
            handler.handle(action, payload);
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