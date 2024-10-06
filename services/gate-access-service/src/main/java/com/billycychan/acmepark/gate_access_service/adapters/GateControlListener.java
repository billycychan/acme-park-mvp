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
public class GateControlListener {

    private final GateControlPort gateControl;

    private static final String OPEN_GATE = "open_gate";
    private static final String CLOSE_GATE = "close_gate";

    public GateControlListener(GateControlPort gateControl) {
        this.gateControl = gateControl;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "parking.access.lotA.gate1", durable = "true"),
            exchange = @Exchange(value = "${app.custom.messaging.inbound-exchange-topic}",
                    ignoreDeclarationExceptions = "true", type = "topic"),
            key = "*"))
    public void listen(String data) {
        log.debug("Receiving request {}", data);
        GateRequest request = translate(data);
        String gate = request.getGate();
        String parkingLot = request.getParkingLot();

        switch (request.getAction().toLowerCase()) {
            case OPEN_GATE:
                gateControl.openGate(parkingLot, gate);
                break;
            case CLOSE_GATE:
                gateControl.closeGate(parkingLot, gate);
                break;
            default:
                log.error("UNEXPECTED GateRequest Received {}", request);
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
