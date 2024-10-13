package com.billycychan.acmepark.access_control_service.adapters;

import com.billycychan.acmepark.access_control_service.adapters.action.ActionHandlerRegistry;
import com.billycychan.acmepark.access_control_service.dto.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AMQPListener {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private final ActionHandlerRegistry registry;

    public AMQPListener(ActionHandlerRegistry registry) {
        this.registry = registry;
    }

    @RabbitListener(queues = {"access.request.queue", "permit.validate.response.queue"})
    public void listen(String data) {
        log.info("Received data,{}, ", data);
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
            throw new RuntimeException(e);
        }
    }
}