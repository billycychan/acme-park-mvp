package com.billycychan.acmepark.permit_service.ports;

import com.billycychan.acmepark.permit_service.dto.TransponderAccessRequest;

public interface PermitValidationUseCase {
    boolean validatePermit(TransponderAccessRequest request);
}
