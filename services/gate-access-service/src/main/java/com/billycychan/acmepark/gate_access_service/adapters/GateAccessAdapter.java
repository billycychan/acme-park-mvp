package com.billycychan.acmepark.gate_access_service.adapters;

import com.billycychan.acmepark.gate_access_service.dto.AccessResult;
import com.billycychan.acmepark.gate_access_service.dto.PermitValidatedEvent;
import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.gate_access_service.ports.incoming.GateAccessPort;
import com.billycychan.acmepark.gate_access_service.ports.outgoing.AccessEventPublisher;
import com.billycychan.acmepark.gate_access_service.ports.outgoing.AccessEventValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class GateAccessAdapter implements GateAccessPort {

    private final AccessEventPublisher eventPublisher;
    private final Map<String, CompletableFuture<AccessResult>> pendingRequests = new ConcurrentHashMap<>();

    public GateAccessAdapter(AccessEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public AccessResult validateTransponderAccess(TransponderAccessRequest request) {
        CompletableFuture<AccessResult> future = new CompletableFuture<>();
        pendingRequests.put(request.getTransponderId(), future);
        eventPublisher.publish(request);

        try {
            return future.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            pendingRequests.remove(request.getTransponderId());
            return new AccessResult(
                    request.getTransponderId(),
                    PermitValidatedEvent.TIMEOUT,
                    "Opps! Validation Timeout.");
        }
    }

    @RabbitListener(queues = "permit.validated.response.queue")
    public void handlePermitValidated(AccessResult result) {
        log.info("Received result: {}", result);
        String transponderId = result.transponderId();
        CompletableFuture<AccessResult> future = pendingRequests.remove(transponderId);
        if (future != null) {
            future.complete(result);
        }
    }
}
