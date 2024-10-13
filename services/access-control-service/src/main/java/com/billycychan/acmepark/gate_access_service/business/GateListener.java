package com.billycychan.acmepark.gate_access_service.business;

import com.billycychan.acmepark.gate_access_service.dto.AccessResult;
import com.billycychan.acmepark.gate_access_service.ports.inbound.ResponseReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GateListener  {

    private final ResponseReceiver responseReceiver;

    public GateListener(ResponseReceiver responseReceiver) {
        this.responseReceiver = responseReceiver;
    }

    @RabbitListener(queues = "permit.validated.response.queue", concurrency = "4")
    public void handlePermitValidated(AccessResult result) {
        responseReceiver.receive(result);
    }
}
