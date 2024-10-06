package com.billycychan.acmepark.permit_service.adapters;

import com.billycychan.acmepark.permit_service.dto.Permit;
import com.billycychan.acmepark.permit_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.permit_service.ports.PermitRepository;
import com.billycychan.acmepark.permit_service.ports.PermitValidationUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PermitValidationService implements PermitValidationUseCase {
    private final PermitRepository permitRepository;

    public PermitValidationService(PermitRepository permitRepository) {
        this.permitRepository = permitRepository;
    }

    @Override
    public boolean validatePermit(TransponderAccessRequest request) {
        return permitRepository.findByTransponderId(request.getTransponderId())
                .map(this::isValidPermit)
                .orElse(false);
    }

    private boolean isValidPermit(Permit permit) {
        return permit.getExpiryDate().isAfter(LocalDateTime.now());
    }
}
