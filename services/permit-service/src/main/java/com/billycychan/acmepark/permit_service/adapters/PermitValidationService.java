package com.billycychan.acmepark.permit_service.adapters;

import com.billycychan.acmepark.permit_service.dto.AccessResult;
import com.billycychan.acmepark.permit_service.dto.Permit;
import com.billycychan.acmepark.permit_service.dto.PermitValidatedEvent;
import com.billycychan.acmepark.permit_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.permit_service.ports.inbound.RequestValidator;
import com.billycychan.acmepark.permit_service.ports.outbound.PermitRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Component
public class PermitValidationService implements RequestValidator {
    private final PermitRepository permitRepository;

    public PermitValidationService(PermitRepository permitRepository) {
        this.permitRepository = permitRepository;
    }

    public AccessResult validateTransponderRequest(TransponderAccessRequest request) {
        AccessResult result = new AccessResult();
        PermitValidatedEvent event = permitRepository.findByTransponderId(request.getTransponderId())
                .map(permit -> isExpired(permit) ? PermitValidatedEvent.EXPIRED : PermitValidatedEvent.VALIDATED)
                .orElse(PermitValidatedEvent.NOT_REGISTERED);
        result.setTransponderId(request.getTransponderId());
        result.setEvent(event);
        result.setMessage(event.getAssociatedMessage());
        return result;
    }

    private boolean isExpired(Permit permit) {
        // Check if the permit has already expired (expiry date is before now)
        return permit.getExpiryDate().isBefore(LocalDateTime.now());
    }
}