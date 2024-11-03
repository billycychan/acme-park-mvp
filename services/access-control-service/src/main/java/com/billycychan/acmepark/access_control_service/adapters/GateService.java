package com.billycychan.acmepark.access_control_service.adapters;

import com.billycychan.acmepark.access_control_service.dto.Message;
import com.billycychan.acmepark.access_control_service.ports.outbound.GateControl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GateService implements GateControl {

    private final ObjectMapper objectMapper;
    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public GateService(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void openGate(String lot, String gate) {
        sendMessage(Actions.OPEN_GATE, lot, gate);
    }

    public void showError(String lot, String gate) {
        sendMessage(Actions.NOT_OPEN_GATE, lot, gate);
    }

    private void sendMessage(String action, String lot, String gate) {
        Message<JsonNode> message = createMessage(action, lot, gate);

        try {
            String serializedMessage = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertSendAndReceive("gate.command", "command", serializedMessage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send message", e);
        }
    }

    private Message<JsonNode> createMessage(String action, String lot, String gate) {
        Message<JsonNode> message = new Message<>();
        message.setAction(action);

        var node = JsonNodeFactory.instance.objectNode();
        node.put("gate", gate);
        node.put("lot", lot);

        message.setPayload(node);
        return message;
    }
}