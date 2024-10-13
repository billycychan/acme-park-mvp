package com.billycychan.acmepark.access_control_service.ports.outbound;

import com.billycychan.acmepark.access_control_service.dto.TransponderAccess;

public interface PermitValidation {
    void send(TransponderAccess access);
}
