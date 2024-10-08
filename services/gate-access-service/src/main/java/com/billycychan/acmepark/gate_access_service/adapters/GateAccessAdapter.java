package com.billycychan.acmepark.gate_access_service.adapters;

import com.billycychan.acmepark.gate_access_service.dto.AccessResult;
import com.billycychan.acmepark.gate_access_service.dto.PermitValidatedEvent;
import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.gate_access_service.ports.incoming.GateAccessPort;
import com.billycychan.acmepark.gate_access_service.ports.outgoing.AccessEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class GateAccessAdapter implements GateAccessPort {

    private final AccessEventPublisher eventPublisher;

    public GateAccessAdapter(AccessEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void vadlidateAccess(TransponderAccessRequest request) {
        eventPublisher.publish(request);
    }

    @RabbitListener(queues = "permit.validated.response.queue", concurrency = "4")
    public void handlePermitValidated(AccessResult result) {
        log.info("Received item from the queue response.queue: {}", result);
    }
}
