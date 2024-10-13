package com.billycychan.acmepark.access_control_service.adapters.action.handlers;

import com.billycychan.acmepark.access_control_service.dto.TransponderAccess;
import com.billycychan.acmepark.access_control_service.ports.inbound.AccessRequestHandling;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EnterGateTransponderHandler implements ActionHandler<JsonNode> {
    private final ObjectMapper objectMapper;
    private final AccessRequestHandling accessRequestHandling;

    @Autowired
    public EnterGateTransponderHandler(ObjectMapper objectMapper, AccessRequestHandling accessRequestHandling) {
        this.objectMapper = objectMapper;
        this.accessRequestHandling = accessRequestHandling;
    }

    @Override
    public void handle(String action, JsonNode payload) {
        try {
            var result = objectMapper.treeToValue(payload, TransponderAccess.class);
            log.info("Handling Transponder Entry Request: {}", payload);
            accessRequestHandling.receiveTransponderEntryRequest(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
