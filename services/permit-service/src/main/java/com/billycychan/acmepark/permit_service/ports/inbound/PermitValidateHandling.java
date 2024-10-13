package com.billycychan.acmepark.permit_service.ports.inbound;

import com.billycychan.acmepark.permit_service.dto.TransponderAccess;

public interface PermitValidateHandling {
    void receivePermitValidateRequest(TransponderAccess access);
}
