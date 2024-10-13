package com.billycychan.acmepark.permit_service.adapters.action.handlers;

import com.billycychan.acmepark.permit_service.dto.TransponderAccess;
import com.billycychan.acmepark.permit_service.ports.inbound.PermitValidateHandling;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PermitValidateRequestHandler implements ActionHandler<JsonNode> {
    private final ObjectMapper objectMapper;
    private final PermitValidateHandling permitValidateHandling;

    @Autowired
    public PermitValidateRequestHandler(ObjectMapper objectMapper, PermitValidateHandling permitValidateHandling) {
        this.objectMapper = objectMapper;
        this.permitValidateHandling = permitValidateHandling;
    }

    @Override
    public void handle(String action, JsonNode payload) {
        try {
            var result = objectMapper.treeToValue(payload, TransponderAccess.class);
            log.info("Handling Permit Validate Request: {}", payload);
            permitValidateHandling.receivePermitValidateRequest(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
