package com.billycychan.acmepark.gate_access_service.adapters;

import com.billycychan.acmepark.gate_access_service.dto.GateRequest;
import com.billycychan.acmepark.gate_access_service.ports.GateControlPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GateControlListener implements GateControlPort {

    private static final String GATE_OPEN = "gate_open";
    private static final String GATE_CLOSE = "gate_close";

    @Override
    public void loadingGate(String gateId) {
        String message = String.format("I am a GateControlPanel. I am validating the access at %s !!! :-)", gateId);
        log.info(message);
    }

    @Override
    public void openGate(String gateId) {
        String message = String.format("I am a GateControlPanel. I am opening the gate %s !!! :-)", gateId);
        log.info(message);
    }

    @Override
    public void closeGate(String gateId) {
        String message = String.format("I am a GateControlPanel. I am closing the gate %s !!! :-)", gateId);
        log.info(message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "parking.access.lotA.gate1", durable = "true"),
            exchange = @Exchange(value = "${app.custom.messaging.inbound-exchange-topic}",
                    ignoreDeclarationExceptions = "true", type = "topic"),
            key = "*"))
    public void listen(String data) {
        log.debug("Receiving request {}", data);
        GateRequest request = translate(data);
        String gateId = request.getGate();
        switch (request.getAction().toLowerCase()) {
            case GATE_OPEN:
                openGate(gateId);
                break;
            case GATE_CLOSE:
                closeGate(gateId);
                break;
            default:
                log.error("UNEXPECTED GateRequest Received");
        }
    }

    private GateRequest translate(String raw) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(raw, GateRequest.class);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
